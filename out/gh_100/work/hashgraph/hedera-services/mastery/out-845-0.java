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


}
