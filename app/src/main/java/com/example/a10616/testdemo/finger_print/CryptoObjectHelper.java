package com.example.a10616.testdemo.finger_print;

import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

import java.security.Key;
import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

/**
 * Created by gaoyang on 2018/06/26.
 */
@RequiresApi(Build.VERSION_CODES.M)
public class CryptoObjectHelper {
    // key
    static final String KEY_NAME = "com.example.a10616.testdemo.finger_print.CryptoObjectHelper";

    // keyStore的名字
    static final String KEYSTORE_NAME = "AndroidKeyStore";

    // 加密方式
    static final String KEY_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES;
    static final String BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC;
    static final String ENCRYPTION_PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7;
    static final String TRANSFORMATION = KEY_ALGORITHM + "/" +
            BLOCK_MODE + "/" +
            ENCRYPTION_PADDING;
    // AES/CBC/PKCS7Padding
    private KeyStore keyStore;


    public FingerprintManagerCompat.CryptoObject buildCryptoObject() {
        Cipher cipher = createCipher();
        if (cipher != null) {
            return new FingerprintManagerCompat.CryptoObject(cipher);
        }
        return null;
    }

    Cipher createCipher() {
        Cipher cipher = null;
        try {
            // Keystore不只是可以保存密码，还可以保存敏感数据 . 好像存在硬件中

            // 通过名字找到秘钥的存储库
            keyStore = KeyStore.getInstance(KEYSTORE_NAME);
            // 加载密码库
            keyStore.load(null);

            Key key = GetKey();
            // TRANSFORMATION  “算法/工作模式/填充模式
            cipher = Cipher.getInstance(TRANSFORMATION);
            //  用密钥初始化此 Cipher对象,密码可以用于加密和解密
            cipher.init(Cipher.ENCRYPT_MODE | Cipher.DECRYPT_MODE, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cipher;
    }

    Key GetKey() throws Exception {
        Key secretKey;
        if (!keyStore.isKeyEntry(KEY_NAME)) {
            CreateKey();
        }

        secretKey = keyStore.getKey(KEY_NAME, null);
        return secretKey;
    }

    void CreateKey() throws Exception {
        // 使用AES加密,秘钥保存在 AndroidKeyStore 中
        KeyGenerator keyGen = KeyGenerator.getInstance(KEY_ALGORITHM, KEYSTORE_NAME);
        KeyGenParameterSpec keyGenSpec =
                // 定义秘钥名称 ,以及秘钥的用途是加密和解密
                new KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        // 加密模式
                        .setBlockModes(BLOCK_MODE)
                        // 加密的padding , 加上前面三句,就是 AES/CBC/PKCS7Padding
                        .setEncryptionPaddings(ENCRYPTION_PADDING)
                        // 需要用户认证
                        .setUserAuthenticationRequired(true)
                        .build();
        keyGen.init(keyGenSpec);
        // 生成key
        keyGen.generateKey();
    }
}