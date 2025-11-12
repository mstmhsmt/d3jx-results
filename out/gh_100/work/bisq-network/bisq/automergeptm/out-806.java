package io.bisq.core.trade;
import com.google.protobuf.Message;
import io.bisq.common.app.Version;
import io.bisq.common.storage.Storage;
import io.bisq.core.btc.wallet.BtcWalletService;
import io.bisq.core.offer.Offer;
import io.bisq.core.trade.protocol.BuyerAsTakerProtocol;
import io.bisq.core.trade.protocol.TakerProtocol;
import io.bisq.generated.protobuffer.PB;
import io.bisq.network.p2p.NodeAddress;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.Coin;
import static com.google.common.base.Preconditions.checkArgument;

@Slf4j public final class BuyerAsTakerTrade extends BuyerTrade implements TakerTrade {
  private static final long serialVersionUID = Version.LOCAL_DB_VERSION;

  public BuyerAsTakerTrade(Offer offer, Coin tradeAmount, Coin txFee, Coin takerFee, boolean isCurrencyForTakerFeeBtc, long tradePrice, NodeAddress tradingPeerNodeAddress, Storage<? extends TradableList> storage, BtcWalletService btcWalletService) {
    super(offer, tradeAmount, txFee, takerFee, isCurrencyForTakerFeeBtc, tradePrice, tradingPeerNodeAddress, storage, btcWalletService);
  }

  @Override public void takeAvailableOffer() {
    checkArgument(tradeProtocol instanceof TakerProtocol, "tradeProtocol NOT instanceof TakerProtocol");
    ((TakerProtocol) tradeProtocol).takeAvailableOffer();
  }

  @Override public PB.Tradable toProto() {
    return PB.Tradable.newBuilder().setBuyerAsTakerTrade(PB.BuyerAsTakerTrade.newBuilder().setTrade((PB.Trade) super.toProto())).build();
  }

  public static Tradable fromProto(PB.BuyerAsTakerTrade proto, Storage<? extends TradableList> storage, BtcWalletService btcWalletService) {
    PB.Trade trade = proto.getTrade();
    return new BuyerAsTakerTrade(Offer.fromProto(trade.getOffer()), Coin.valueOf(trade.getTxFeeAsLong()), Coin.valueOf(trade.getTakerFeeAsLong()), Coin.valueOf(trade.getTakerFeeAsLong()), trade.getIsCurrencyForTakerFeeBtc(), trade.getTradePrice(), NodeAddress.fromProto(trade.getTradingPeerNodeAddress()), storage, btcWalletService);
  }

  @Override protected void createTradeProtocol() {
    tradeProtocol = new BuyerAsTakerProtocol(this);
  }
}