package com.hedera.services.legacy.client.test;

import com.google.protobuf.ByteString;
import com.hedera.services.legacy.client.util.Common;
import com.hedera.services.legacy.client.util.Ed25519KeyStore;
import com.hederahashgraph.api.proto.java.AccountID;
import com.hederahashgraph.api.proto.java.Key;
import com.hederahashgraph.api.proto.java.Transaction;
import com.hederahashgraph.api.proto.java.TransactionBody;
import com.hederahashgraph.builder.RequestBuilder;
import io.grpc.StatusRuntimeException;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyPair;

public class CreateAccountPemFile extends ClientBaseThread {

    private static final Logger log = LogManager.getLogger(CryptoCreate.class);

    private String DEFAULT_PASSWORD = "password";

    private long initialBalance = 100000L;

    private int numberOfIterations = 1;

    private AccountID payerAccount;

    public CreateAccountPemFile(String host, int port, long nodeAccountNumber, String[] args, int index) {
        super(host, port, nodeAccountNumber, args, index);
        this.nodeAccountNumber = nodeAccountNumber;
        this.host = host;
        this.port = port;
        if ((args.length) > 0) {
            numberOfIterations = Integer.parseInt(args[0]);
        }
        if ((args.length) > 1) {
            initialBalance = Long.parseLong(args[1]);
        }
        if ((args.length) > 2) {
            DEFAULT_PASSWORD = args[2];
        }
        try {
            initAccountsAndChannels();
            payerAccount = genesisAccount;
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    void demo() throws Exception 
<<<<<<< commits-gh_100/hashgraph/hedera-services/b1ffe2a9813ccbf7535a988e99d75242cabef0dc/CreateAccountPemFile-40cc446.java

=======
{
        try {
            for (int i = 0; i < numberOfIterations; i++) {
                final Ed25519KeyStore keyStore = new Ed25519KeyStore(DEFAULT_PASSWORD.toCharArray());
                final KeyPair newKeyPair = keyStore.insertNewKeyPair();
                if (useSigMap) {
                    Common.addKeyMap(newKeyPair, pubKey2privKeyMap);
                }
                AccountID newAccount = RequestBuilder.getAccountIdBuild(nodeAccountNumber, 0l, 0l);
                try {
                    Transaction transaction = Common.tranSubmit(() -> {
                        Transaction createRequest;
                        try {
                            if (useSigMap) {
                                byte[] pubKey = ((EdDSAPublicKey) newKeyPair.getPublic()).getAbyte();
                                Key key = Key.newBuilder().setEd25519(ByteString.copyFrom(pubKey)).build();
                                Key payerKey = acc2ComplexKeyMap.get(payerAccount);
                                createRequest = Common.createAccountComplex(payerAccount, payerKey, newAccount, key, initialBalance, pubKey2privKeyMap);
                            } else {
                                createRequest = TestHelper.createAccountWithFee(payerAccount, newAccount, newKeyPair, initialBalance, Collections.singletonList(genesisPrivateKey));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                        return createRequest;
                    }, stub::createAccount);
                    AccountID createdAccount = Common.getAccountIDfromReceipt(stub, TransactionBody.parseFrom(transaction.getBodyBytes()).getTransactionID());
                    String pemFileName = "account_" + createdAccount.getAccountNum() + ".pem";
                    keyStore.write(pemFileName);
                    long balance = Common.getAccountBalance(stub, createdAccount, payerAccount, genesisKeyPair, nodeAccount);
                    Assert.assertEquals(balance, initialBalance);
                    log.info("----------------------------------------");
                    log.info("ATTENTION !!  Account {} created with balance {} and its pem file is {}, password is:{}", createdAccount.getAccountNum(), initialBalance, pemFileName, DEFAULT_PASSWORD);
                    log.info("----------------------------------------");
                    final Ed25519KeyStore restoreKeyStore = new Ed25519KeyStore(DEFAULT_PASSWORD.toCharArray(), pemFileName);
                    KeyPair restoredKeyPair = restoreKeyStore.get(0);
                    log.info("Read back generated pem file successfully ");
                } catch (StatusRuntimeException e) {
                    if (!tryReconnect(e)) {
                        return;
                    }
                } catch (Exception e) {
                    log.error("Unexpected error ", e);
                    return;
                }
            }
        } finally {
            channel.shutdown();
        }
    }
>>>>>>> commits-gh_100/hashgraph/hedera-services/37eb829cb2b7e821eec51a8b1524bf1460f6151a/CreateAccountPemFile-4766993.java

}
