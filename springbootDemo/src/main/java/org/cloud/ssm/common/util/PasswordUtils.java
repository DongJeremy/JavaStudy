package org.cloud.ssm.common.util;

import java.util.Random;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.crypto.hash.DefaultHashService;

public class PasswordUtils {
    /**
     * 获得密码
     * 
     * @param len 密码长度
     * @return
     */
    public static String createPassWord(int len) {
        int random = createRandomInt();
        return createPassWord(random, len);
    }

    private static String createPassWord(int random, int len) {
        Random rd = new Random(random);
        final int maxNum = 62;
        StringBuffer sb = new StringBuffer();
        int rdGet;// 取得随机数
        char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
                't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9' };

        int count = 0;
        while (count < len) {
            rdGet = Math.abs(rd.nextInt(maxNum));// 生成的数最大为62-1
            if (rdGet >= 0 && rdGet < str.length) {
                sb.append(str[rdGet]);
                count++;
            }
        }
        return sb.toString();
    }

    private static int createRandomInt() {
        // 得到0.0到1.0之间的数字，并扩大100000倍
        double temp = Math.random() * 100000;
        // 如果数据等于100000，则减少1
        if (temp >= 100000) {
            temp = 99999;
        }
        int tempint = (int) Math.ceil(temp);
        return tempint;
    }

    public static String generatePassword(String plainString) {
        DefaultHashService hashService = new DefaultHashService();
        hashService.setHashIterations(50000); // 500000
        hashService.setHashAlgorithmName("SHA-256");
        hashService.setGeneratePublicSalt(true);
        DefaultPasswordService passwordService = new DefaultPasswordService();
        passwordService.setHashService(hashService);
        String encryptedPassword = passwordService.encryptPassword(plainString);
        return encryptedPassword;
    }

    public static void main(String[] args) {
        DefaultHashService hashService = new DefaultHashService();
        hashService.setHashIterations(50000); // 500000
        hashService.setHashAlgorithmName("SHA-256");
        // hashService.setPrivateSalt(new
        // SimpleByteSource("myVERYSECRETBase64EncodedSalt")); // Same salt as in
        // shiro.ini, but NOT base64-encoded.
        hashService.setGeneratePublicSalt(true);

        DefaultPasswordService passwordService = new DefaultPasswordService();
        passwordService.setHashService(hashService);
        String encryptedPassword = passwordService.encryptPassword("password");
        System.out.println(encryptedPassword);
        System.out.println(createPassWord(32));
    }
}
