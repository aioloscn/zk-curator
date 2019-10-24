package com.aiolos.curator;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.security.NoSuchAlgorithmException;

/**
 * @author Aiolos
 * @date 2019-10-24 10:02
 */
public class AclUtils {

    public static String getDigestUserPwd(String id) {

        String digest = StringUtils.EMPTY;
        try {
            digest = DigestAuthenticationProvider.generateDigest(id);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return digest;
    }

    public static void main(String[] args) {

        String id = "super:admin";
        String idDigested = getDigestUserPwd(id);
        System.out.println(idDigested);
    }
}
