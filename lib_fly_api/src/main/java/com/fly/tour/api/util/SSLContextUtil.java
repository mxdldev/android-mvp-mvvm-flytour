/*
 * Copyright 2015 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.fly.tour.api.util;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * https 证书工具
 *
 * @author Yan Zhenjie.
 */
public class SSLContextUtil {

  /**
   * 如果不需要https证书.(NoHttp已经修补了系统的SecureRandom的bug)。
   */
  public static SSLContext getDefaultSLLContext() {
    SSLContext sslContext = null;
    try {
      sslContext = SSLContext.getInstance("TLS");
      sslContext.init(null, new TrustManager[] {trustManagers}, new SecureRandom());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sslContext;
  }

  /**
   * 信任管理器
   */
  private static TrustManager trustManagers = new X509TrustManager() {

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType)
        throws CertificateException {}

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType)
        throws CertificateException {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
      return new X509Certificate[0];
    }
  };

  /**
   * 域名验证
   */
  public static final HostnameVerifier HOSTNAME_VERIFIER = new HostnameVerifier() {
    public boolean verify(String hostname, SSLSession session) {
      return true;
    }
  };

}
