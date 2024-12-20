package com.example.thisisredispractice.ch7.cart;

import com.example.thisisredispractice.JedisHelper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.Set;

import static java.util.Objects.isNull;

public class Cart {

    private static final String KEY_CART_LIST = ":cart:product";

    private static final String KEY_CART_PRODUCT = ":cart:productid:";

    private static final String JSON_PRODUCT_LIST = "products";

    private static final int EXPIRE = 60 * 60 * 24 * 3;

    private final Jedis jedis;

    private final JSONObject cartInfo;

    private final String userNo;

    /**
     * 장바구니를 처리하기 위한 Cart 클래스의 생성자
     * @param helper 제디스 도우미 객체
     */
    public Cart(JedisHelper helper, String userNo) {
        this.jedis = helper.getConnection();
        this.userNo = userNo;
        this.cartInfo = getCartInfo();
    }

    /**
     * 레디스에 저장된 장바구니 정보를 조회하여 JSON객체로 변환한다.
     * @return 장바구니 정보가 저장된 JSONObject
     */
    private JSONObject getCartInfo() {
        String productInfo = jedis.get(userNo + KEY_CART_LIST);
        if (!StringUtils.hasText(productInfo)) {
            return makeEmptyCart();
        }

        try {
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(productInfo);
        } catch (ParseException e) {
            return makeEmptyCart();
        }
    }

    /**
     * 장바구니가 존재하지 않는 사용자를 위한 빈 장바구니 정보를 생성한다.
     * @return 빈 장바구니 정보
     */
    private JSONObject makeEmptyCart() {
        JSONObject cart = new JSONObject();
        cart.put(JSON_PRODUCT_LIST, new JSONArray());
        return cart;
    }

    /**
     * 장바구니에 저장된 상품을 삭제한다.
     * @return 삭제된 상품개수
     */
    public int flushCart() {
        JSONArray products = (JSONArray) cartInfo.get(JSON_PRODUCT_LIST);
        for (int i = 0; i < products.size(); i++) {
            jedis.del(userNo + KEY_CART_PRODUCT + products.get(i));
        }

        jedis.set(userNo + KEY_CART_LIST, "");
        return products.size();
    }

    /**
     * 장바구니에 상품을 추가한다.
     * @param productNo 장바구니에 추가할 상품 번호
     * @param productName 장바구니에 추가할 상품명
     * @param quantity 상품의 개수
     * @return 장바구니 등록 결과
     */
    public String addProduct(String productNo, String productName, int quantity) {
        JSONArray products = (JSONArray) cartInfo.get(JSON_PRODUCT_LIST);
        products.add(productNo);

        jedis.set(userNo + KEY_CART_LIST, cartInfo.toJSONString());

        JSONObject product = new JSONObject();
        product.put("productNo", productNo);
        product.put("productName", productName);
        product.put("quantity", quantity);

        String productKey = userNo + KEY_CART_PRODUCT + productNo;
        return jedis.setex(productKey, EXPIRE, product.toJSONString());
    }

    /**
     * 장바구니에 저장된 상품정보를 삭제한다.
     * @param productNos 삭제할 상품번호 목록
     * @return 삭제된 상품의 개수
     */
    public int deleteProduct(String[] productNos) {
        JSONArray products = (JSONArray) cartInfo.get(JSON_PRODUCT_LIST);
        int result = 0;
        for (String productNo : productNos) {
            products.remove(productNo);
            result += jedis.del(userNo + KEY_CART_PRODUCT + productNo);
        }
        jedis.set(userNo + KEY_CART_LIST, cartInfo.toJSONString());

        return result;
    }

    /**
     * 장바구니에 저장된 상품 목록을 JSONArray형식으로 조회한다.
     * @return 조회된 상품목록
     */
    public JSONArray getProductList() {
        boolean isChanged = false;
        JSONArray products = (JSONArray) cartInfo.get(JSON_PRODUCT_LIST);
        JSONArray result = new JSONArray();

        for (int i = 0; i < products.size(); i++) {
            String value = jedis.get(userNo + KEY_CART_PRODUCT + products.get(i));
            if (isNull(value)) {
                isChanged = true;
            } else {
                result.add(value);
            }
        }

        if (isChanged) {
            jedis.set(userNo + KEY_CART_LIST, cartInfo.toJSONString());
        }

        return result;
    }

    /**
     * keys 명령을 사용하여 장바구니에 저장된 상품을 삭제한다.
     * @return 삭제된 상품개수
     * @deprecated keys 명령을 사용한 잘못된 구현이다.
     */
    public int flushCartDeprecated() {
        Set<String> keys = jedis.keys(userNo + KEY_CART_PRODUCT + "*");

        for (String key : keys) {
            jedis.del(key);
        }

        jedis.set(userNo + KEY_CART_LIST, "");

        return keys.size();
    }
}
