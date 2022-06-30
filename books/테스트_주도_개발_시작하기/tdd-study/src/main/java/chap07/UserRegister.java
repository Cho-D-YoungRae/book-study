package chap07;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRegister {

    private final WeakPasswordChecker passwordChecker;

    private final UserRepository userRepository;

    private final EmailNotifier emailNotifier;

    public void register(String id, String pw, String email) {
        if (passwordChecker.checkPasswordWeak(pw)) {
            throw new WeakPasswordException();
        }
        User user = userRepository.findById(id);
        if (user != null) {
            throw new DupIdException();
        }
        userRepository.save(new User(id, pw, email));

        emailNotifier.sendRegisterEmail(email);
    }
}
