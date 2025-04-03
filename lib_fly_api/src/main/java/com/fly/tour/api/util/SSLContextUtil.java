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
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateNotYetValidException;
import java.security.KeyStore;
import java.security.Principal;
import java.security.PublicKey;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.NoSuchProviderException;
import java.security.InvalidKeyException;
import java.util.Date;
import java.util.Set;
import java.math.BigInteger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * https 证书工具
 *
 * @author Yan Zhenjie.
 */
public class SSLContextUtil {

  /**
   * Initialize the default trust manager for certificate validation
   */
  private static X509TrustManager getDefaultTrustManager() {
    try {
      TrustManagerFactory tmf = TrustManagerFactory.getInstance(
          TrustManagerFactory.getDefaultAlgorithm());
      tmf.init((KeyStore) null);
      for (TrustManager tm : tmf.getTrustManagers()) {
        if (tm instanceof X509TrustManager) {
          return (X509TrustManager) tm;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * The default system trust manager for certificate validation
   */
  private static final X509TrustManager finalTm = getDefaultTrustManager();

  /**
   * Custom certificate implementation that allows for time leeway
   */
  private static class TimeLeewayCertificate extends X509Certificate {
    private final X509Certificate originalCertificate;
    private static final long TIME_LEEWAY_MS = 24 * 60 * 60 * 1000; // 24 hours

    public TimeLeewayCertificate(X509Certificate originalCertificate) {
      this.originalCertificate = originalCertificate;
    }

    @Override
    public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
      // Use current date for validation but add leeway in the implementation
      checkValidity(new Date());
    }

    @Override
    public void checkValidity(Date date) throws CertificateExpiredException, CertificateNotYetValidException {
      // Add leeway to certificate validation dates
      Date notBefore = getNotBefore();
      Date notAfter = getNotAfter();
      Date adjustedDate = new Date(date.getTime());

      if (adjustedDate.before(new Date(notBefore.getTime() - TIME_LEEWAY_MS))) {
        throw new CertificateNotYetValidException("Certificate not yet valid: " 
            + notBefore.toString());
      }
      
      if (adjustedDate.after(new Date(notAfter.getTime() + TIME_LEEWAY_MS))) {
        throw new CertificateExpiredException("Certificate expired at " 
            + notAfter.toString());
      }
    }

    @Override
    public int getVersion() {
      return originalCertificate.getVersion();
    }

    @Override
    public BigInteger getSerialNumber() {
      return originalCertificate.getSerialNumber();
    }

    @Override
    public Principal getIssuerDN() {
      return originalCertificate.getIssuerDN();
    }

    @Override
    public Principal getSubjectDN() {
      return originalCertificate.getSubjectDN();
    }

    @Override
    public Date getNotBefore() {
      return originalCertificate.getNotBefore();
    }

    @Override
    public Date getNotAfter() {
      return originalCertificate.getNotAfter();
    }

    @Override
    public byte[] getTBSCertificate() throws CertificateEncodingException {
      return originalCertificate.getTBSCertificate();
    }

    @Override
    public byte[] getSignature() {
      return originalCertificate.getSignature();
    }

    @Override
    public String getSigAlgName() {
      return originalCertificate.getSigAlgName();
    }

    @Override
    public String getSigAlgOID() {
      return originalCertificate.getSigAlgOID();
    }

    @Override
    public byte[] getSigAlgParams() {
      return originalCertificate.getSigAlgParams();
    }

    @Override
    public boolean[] getIssuerUniqueID() {
      return originalCertificate.getIssuerUniqueID();
    }

    @Override
    public boolean[] getSubjectUniqueID() {
      return originalCertificate.getSubjectUniqueID();
    }

    @Override
    public boolean[] getKeyUsage() {
      return originalCertificate.getKeyUsage();
    }

    @Override
    public int getBasicConstraints() {
      return originalCertificate.getBasicConstraints();
    }

    @Override
    public byte[] getEncoded() throws CertificateEncodingException {
      return originalCertificate.getEncoded();
    }

    @Override
    public void verify(PublicKey key) throws CertificateException,
        NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
      originalCertificate.verify(key);
    }

    @Override
    public void verify(PublicKey key, String sigProvider) throws CertificateException,
        NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
      originalCertificate.verify(key, sigProvider);
    }

    @Override
    public String toString() {
      return originalCertificate.toString();
    }

    @Override
    public PublicKey getPublicKey() {
      return originalCertificate.getPublicKey();
    }

    @Override
    public Set<String> getCriticalExtensionOIDs() {
      return originalCertificate.getCriticalExtensionOIDs();
    }

    @Override
    public byte[] getExtensionValue(String oid) {
      return originalCertificate.getExtensionValue(oid);
    }

    @Override
    public Set<String> getNonCriticalExtensionOIDs() {
      return originalCertificate.getNonCriticalExtensionOIDs();
    }

    @Override
    public boolean hasUnsupportedCriticalExtension() {
      return originalCertificate.hasUnsupportedCriticalExtension();
    }
  }

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
        throws CertificateException {
      if (finalTm != null) {
        finalTm.checkClientTrusted(chain, authType);
      }
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType)
        throws CertificateException {
      if (finalTm != null) {
        // replace the certificates with ones that have time leeway
        X509Certificate[] timeLeewayChain = new X509Certificate[chain.length];
        for (int i = 0; i < chain.length; i++) {
          timeLeewayChain[i] = new TimeLeewayCertificate(chain[i]);
        }
        finalTm.checkServerTrusted(timeLeewayChain, authType);
      }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
      return finalTm != null ? finalTm.getAcceptedIssuers() : new X509Certificate[0];
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

  /**
   * Get a strict SSLContext that uses the system's default certificate validation
   */
  public static SSLContext getStrictSSLContext() {
    try {
      return SSLContext.getDefault();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
  }
}
