package org.lamb.framework.core.algorithm;


import org.lamb.framework.common.enumeration.ExceptionEnum;
import org.lamb.framework.common.exception.EventException;
import org.lamb.framework.core.algorithm.enumeration.AlgorithmEnum;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.*;

/**
 * Created by WangGang on 2017/6/22 0022.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */
public class RSACoderAlgorithm {

    /**
     * 加密算法
     */
    public static final String KEY_ALGORTHM = "RSA";

    /**具体加密算法，包括padding的方式*/
    public static final String SPECIFIC_KEY_ALGORITHM = "RSA/ECB/PKCS1Padding";

    /**
     * 用RSA算法进行加密
     * 
     * @param paramsString 要加密的字符串
     * @param charset 字符集
     * @param publicKey 加密使用的公钥
     * @param encryptionType 加密算法类型
     * @return  加密结果
     */
    public static String encrypt(String paramsString, String charset, String publicKey, AlgorithmEnum encryptionType) {
        try {
            byte[] encryptedResult = RSACoderAlgorithm.encryptByPublicKey(paramsString.getBytes(charset),
                    publicKey, encryptionType);

            return Base64Algorithm.byteArrayToBase64(encryptedResult);
        } catch (UnsupportedEncodingException e) {
            throw new EventException(ExceptionEnum.ES00000005);
        }
    }

    /**
     * 把参数通过私钥进行加签.
     * 
     * @param signType 签名类型
     * @param data 要加密的字符串
     * @param charset 字符串的编码
     * @param privateKey 商户的私钥
     * @return 加签后的数据
     */
    public static String sign(String data, String charset, String privateKey,AlgorithmEnum signType) {
        try {
            byte[] dataInBytes = data.getBytes(charset);
            String signParams = RSACoderAlgorithm.sign(dataInBytes, privateKey,signType);//用应用的私钥加签.
            return signParams;
        } catch (UnsupportedEncodingException e) {
            throw new EventException(ExceptionEnum.ES00000005);
        }
    }

    /**
     * 解密数据
     * 
     * @param data base64格式的加密数据
     * @param key 私钥
     * @param charset 字符集
     * @return 解密后的明文
     */
    public static String decrypt(String data, String key, String charset) {
        try {
            byte[] byte64 = Base64Algorithm.base64ToByteArray(data);
            byte[] encryptedBytes = decryptByPrivateKey(byte64, key, null);
            return new String(encryptedBytes, charset);
        } catch (UnsupportedEncodingException e) {
            throw new EventException(ExceptionEnum.ES00000005);
        }

    }

    /**
     * 解密数据
     * 
     * @param data base64格式的加密数据
     * @param key 私钥
     * @param charset 字符集
     * @param encryptionType 加密类型
     * @return 解密后的明文
     */
    public static String decrypt(String data, String key, String charset, AlgorithmEnum encryptionType) {
        try {
            byte[] byte64 = Base64Algorithm.base64ToByteArray(data);
            byte[] encryptedBytes = decryptByPrivateKey(byte64, key, encryptionType);
            return new String(encryptedBytes, charset);
        } catch (UnsupportedEncodingException e) {
            throw new EventException(ExceptionEnum.ES00000005);
        }
    }

    /**
     * 用私钥解密 
     * @param data   加密数据
     * @param key    密钥
     * @param encryptionType 加密类型
     * @return 解密后的byte数组
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key, AlgorithmEnum encryptionType) {
        try {
            byte[] decryptedData = null;

            //对私钥解密
            byte[] keyBytes = decryptBASE64(key);

            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
            Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            //对数据解密
            Cipher cipher = Cipher.getInstance(SPECIFIC_KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            // 解密时超过maxDecryptBlockSize字节就报错。为此采用分段解密的办法来解密
            int maxDecryptBlockSize;
            if (encryptionType != null) {
                maxDecryptBlockSize = getMaxDecryptBlockSizeByEncryptionType(encryptionType);
            } else {
                maxDecryptBlockSize = getMaxDecryptBlockSize(keyFactory, privateKey);
            }

            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            try {
                int dataLength = data.length;
                for (int i = 0; i < dataLength; i += maxDecryptBlockSize) {
                    int decryptLength = dataLength - i < maxDecryptBlockSize ? dataLength - i
                            : maxDecryptBlockSize;
                    byte[] doFinal = cipher.doFinal(data, i, decryptLength);
                    bout.write(doFinal);
                }
                decryptedData = bout.toByteArray();
            } finally {
                if (bout != null) {
                    bout.close();
                }
            }

            return decryptedData;
        } catch (IOException e) {
            throw new EventException(ExceptionEnum.ES00000008);
        } catch (NoSuchAlgorithmException e) {
            throw new EventException(ExceptionEnum.ES00000006);
        } catch (InvalidKeyException e) {
            throw new EventException(ExceptionEnum.ES00000009);
        } catch (NoSuchPaddingException e) {
            throw new EventException(ExceptionEnum.ES00000007);
        } catch (BadPaddingException e) {
            throw new EventException(ExceptionEnum.ES00000010);
        }  catch (IllegalBlockSizeException e) {
            throw new EventException(ExceptionEnum.ES00000011);
        } catch (InvalidKeySpecException e) {
            throw new EventException(ExceptionEnum.ES00000012);
        }

    }

    /**
     * 用公钥加密
     * @param data   加密数据
     * @param key    公钥
     * @param encryptionType 加密类型
     * @return 加密后的字节数组
     * @throws Exception 异常
     */
    public static byte[] encryptByPublicKey(byte[] data, String key,AlgorithmEnum encryptionType) {
        try {
            byte[] encryptedData = null;

            //对公钥解密
            byte[] keyBytes = decryptBASE64(key);
            //取公钥
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
            Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

            //对数据解密
            Cipher cipher = Cipher.getInstance(SPECIFIC_KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            // 加密时超过maxEncryptBlockSize字节就报错。为此采用分段加密的办法来加密
            int maxEncryptBlockSize;
            if (encryptionType != null) {
                maxEncryptBlockSize = getMaxEncryptBlockSizeByEncryptionType(encryptionType);
            } else {
                maxEncryptBlockSize = getMaxEncryptBlockSize(keyFactory, publicKey);
            }

            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            try {
                int dataLength = data.length;
                for (int i = 0; i < data.length; i += maxEncryptBlockSize) {
                    int encryptLength = dataLength - i < maxEncryptBlockSize ? dataLength - i
                            : maxEncryptBlockSize;
                    byte[] doFinal = cipher.doFinal(data, i, encryptLength);
                    bout.write(doFinal);
                }
                encryptedData = bout.toByteArray();
            } finally {
                if (bout != null) {
                    bout.close();
                }
            }
            return encryptedData;
        } catch (IOException e) {
            throw new EventException(ExceptionEnum.ES00000013);
        } catch (NoSuchAlgorithmException e) {
            throw new EventException(ExceptionEnum.ES00000006);
        } catch (InvalidKeyException e) {
            throw new EventException(ExceptionEnum.ES00000014);
        } catch (NoSuchPaddingException e) {
            throw new EventException(ExceptionEnum.ES00000007);
        } catch (BadPaddingException e) {
            throw new EventException(ExceptionEnum.ES00000015);
        } catch (InvalidKeySpecException e) {
            throw new EventException(ExceptionEnum.ES00000016);
        } catch (IllegalBlockSizeException e) {
            throw new EventException(ExceptionEnum.ES00000017);
        }

    }

    /** 用私钥对信息生成数字签名
     * @param signType 签名类型
     * @param data   加密数据
     * @param privateKey 私钥
     * @return 签名后的base64值
     */
    public static String sign(byte[] data, String privateKey,AlgorithmEnum signType) {

        try {
            //解密私钥
            byte[] keyBytes = decryptBASE64(privateKey);
            //构造PKCS8EncodedKeySpec对象
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
            //指定加密算法
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
            //取私钥匙对象
            PrivateKey privateKey2 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

            //用私钥对信息生成数字签名
            Signature signature = Signature.getInstance(signType.getDesc());
            signature.initSign(privateKey2);
            signature.update(data);

            return encryptBASE64(signature.sign());
        } catch (NoSuchAlgorithmException e) {
            throw new EventException(ExceptionEnum.ES00000006);
        } catch (SignatureException e) {
            throw new EventException(ExceptionEnum.ES00000018);
        } catch (InvalidKeyException e) {
            throw new EventException(ExceptionEnum.ES00000009);
        } catch (InvalidKeySpecException e) {
            throw new EventException(ExceptionEnum.ES00000012);
        }

    }
    /**
     * 校验数字签名
     * @param signType 签名类型
     * @param data   加密数据
     * @param publicKey  公钥
     * @param sign   数字签名
     * @return 验签结果
     */
    public static boolean verify(byte[] data, String publicKey, String sign,AlgorithmEnum signType) {


        try {
            //解密公钥
            byte[] keyBytes = decryptBASE64(publicKey);
            //构造X509EncodedKeySpec对象
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
            //指定加密算法
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
            //取公钥匙对象
            PublicKey publicKey2 = keyFactory.generatePublic(x509EncodedKeySpec);

            Signature signature = Signature.getInstance(signType.getDesc());
            signature.initVerify(publicKey2);
            signature.update(data);
            //验证签名是否正常
            return signature.verify(decryptBASE64(sign));
        } catch (NoSuchAlgorithmException e) {
            throw new EventException(ExceptionEnum.ES00000006);
        } catch (SignatureException e) {
            throw new EventException(ExceptionEnum.ES00000018);
        } catch (InvalidKeyException e) {
            throw new EventException(ExceptionEnum.ES00000014);
        } catch (InvalidKeySpecException e) {
            throw new EventException(ExceptionEnum.ES00000016);
        }
    }

    /**
     * 获取每次加密的最大长度
     * 
     * @param keyFactory KeyFactory
     * @param key 公钥
     * @return 单词加密最大长度
     */
    private static int getMaxEncryptBlockSize(KeyFactory keyFactory, Key key) {
        try {
            //默认先设置成RSA1024的最大加密长度
            int maxLength = 117;
            RSAPublicKeySpec publicKeySpec = keyFactory.getKeySpec(key, RSAPublicKeySpec.class);
            int keyLength = publicKeySpec.getModulus().bitLength();
            maxLength = keyLength / 8 - 11;
            return maxLength;
        } catch (InvalidKeySpecException e) {
            throw new EventException(ExceptionEnum.ES00000016);
        }

    }

    /**
     * 根据加密算法类型获取单次最大加密长度
     * 
     * @param encryptionType
     * @return
     */
    private static int getMaxEncryptBlockSizeByEncryptionType(AlgorithmEnum encryptionType) {
        if (encryptionType == AlgorithmEnum.RSA1024) {
            return 1024 / 8 - 11;
        } else if (encryptionType == AlgorithmEnum.RSA2048) {
            return 2048 / 8 - 11;
        }

        return 1024 / 8 - 11;
    }

    /***
     * 获取每次解密最大长度
     * 
     * @param keyFactory KeyFactory
     * @param key 私钥
     * @return 单次解密最大长度
     */
    private static int getMaxDecryptBlockSize(KeyFactory keyFactory, Key key) {
        try {
            //默认先设置成RSA1024的最大解密长度
            int maxLength = 128;
            RSAPrivateKeySpec publicKeySpec = keyFactory.getKeySpec(key, RSAPrivateKeySpec.class);
            int keyLength = publicKeySpec.getModulus().bitLength();
            maxLength = keyLength / 8;
            return maxLength;
        } catch (InvalidKeySpecException e) {
            throw new EventException(ExceptionEnum.ES00000016);
        }
    }

    /**
     * 
     * 
     * @param encryptionType
     * @return
     */
    private static int getMaxDecryptBlockSizeByEncryptionType(AlgorithmEnum encryptionType) {
        if (encryptionType == AlgorithmEnum.RSA1024) {
            return 1024 / 8;
        } else if (encryptionType == AlgorithmEnum.RSA2048) {
            return 2048 / 8;
        }

        return 1024 / 8;
    }

    /**
     * BASE64解密
     * @param key
     * @return
     */
    public static byte[] decryptBASE64(String key) {
        return Base64Algorithm.base64ToByteArray(key);
    }

    /**
     * BASE64加密
     * @param key
     * @return
     */
    public static String encryptBASE64(byte[] key){
        return Base64Algorithm.byteArrayToBase64(key);
    }

}
