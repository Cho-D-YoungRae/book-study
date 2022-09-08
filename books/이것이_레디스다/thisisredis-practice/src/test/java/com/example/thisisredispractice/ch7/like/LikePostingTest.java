package com.example.thisisredispractice.ch7.like;

import com.example.thisisredispractice.JedisHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class LikePostingTest {

    private static JedisHelper helper;

    private static Random rand = new Random();

    private static int POSTING_COUNT = 20;

    private static String[] POSTLIST = new String[POSTING_COUNT];

    private static int TESTUSER = rand.nextInt(10_000_000);

    private LikePosting likePosting;

    @BeforeAll
    public static void setUpBeforeAll() throws Exception {
        helper = JedisHelper.getInstance();
        for (int i = 0; i < POSTLIST.length; i++) {
            POSTLIST[i] = String.valueOf(i + 1);
        }
    }

    @AfterAll
    public static void tearDownAfterAll() throws Exception {
        helper.destroyPool();
    }

    @BeforeEach
    public void setUp() {
        likePosting = new LikePosting(helper);
    }

    @Test
    void like() {
        String postingNo = String.valueOf(LikePostingTest.rand.nextInt(POSTING_COUNT));
        if (likePosting.isLiked(postingNo, String.valueOf(TESTUSER))) {
            likePosting.unLike(postingNo, String.valueOf(TESTUSER));
        }
        assertThat(likePosting.like(postingNo, String.valueOf(TESTUSER))).isTrue();
    }

    @Test
    void unLike() {
        String postingNo = String.valueOf(LikePostingTest.rand.nextInt(POSTING_COUNT));
        if (likePosting.isLiked(postingNo, String.valueOf(TESTUSER))) {
            assertThat(likePosting.unLike(postingNo, String.valueOf(TESTUSER))).isTrue();
        } else {
            assertThat(likePosting.like(postingNo, String.valueOf(TESTUSER))).isTrue();
            assertThat(likePosting.unLike(postingNo, String.valueOf(TESTUSER))).isTrue();
        }
    }

    @Test
    void getLikeCount() {
        String postingNo = String.valueOf(LikePostingTest.rand.nextInt(POSTING_COUNT));
        if (likePosting.isLiked(postingNo, String.valueOf(TESTUSER))) {
            assertThat(likePosting.unLike(postingNo, String.valueOf(TESTUSER))).isTrue();
        }

        Long prevCount = likePosting.getLikeCount(postingNo);
        likePosting.like(postingNo, String.valueOf(TESTUSER));
        assertThat(likePosting.getLikeCount(postingNo)).isEqualTo(prevCount + 1);
    }

    @Test
    void getLikeCountList() {
        List<Long> countList = likePosting.getLikeCountList(POSTLIST);
        assertThat(countList).hasSize(POSTING_COUNT);
    }

    @Test
    void isLiked() {
        String postingNo = String.valueOf(LikePostingTest.rand.nextInt(POSTING_COUNT));
        likePosting.like(postingNo, String.valueOf(TESTUSER));
        assertThat(likePosting.isLiked(postingNo, String.valueOf(TESTUSER))).isTrue();
    }

    @Test
    void deleteLikeInfo() {
        String postingNo = "1234567890";
        likePosting.like(postingNo, String.valueOf(TESTUSER));
        assertThat(likePosting.deleteLikeInfo(postingNo)).isTrue();
    }

    @Test
    void randomList() {
        for (int i = 0; i < POSTING_COUNT; i++) {
            String sudoRandomUser = String.valueOf(rand.nextInt(10_000_000));
            likePosting.like(String.valueOf(i), sudoRandomUser);
        }
    }
}