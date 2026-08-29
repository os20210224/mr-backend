## ssl carolije

### keystore

generisanje keystore-a

```
keytool -keystore KEY_STORE_NAME -genkey CERTIFICATE_NAME -keyalg RSA
```

dokumentacija kaze da first and last name mora biti pun domen

ja ne posedujem domen

---

uzimanje sertifikata

```
keytool -keystore KEY_STORE_NAME -certreq -alias CERTIFICATE_NAME -keyalg RSA -file CERTIFICATE_FILE_NAME.csr
```

ovime se navodno dobija sertifikat koji moze da se pinnuje na klijentskoj splikaciji

isto tako, pinning dokumentacija daje sledece:

```
openssl s_client -connect [domain]:[port] -showcerts </dev/null 2>/dev/null | openssl x509 -outform DER > sertifikat.der
```
