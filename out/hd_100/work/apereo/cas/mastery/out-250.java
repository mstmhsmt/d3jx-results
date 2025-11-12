package org.jasig.cas.ticket.support;

import org.jasig.cas.ticket.ExpirationPolicy;
import org.jasig.cas.ticket.TicketState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final public class ThrottledUseAndTimeoutExpirationPolicy implements ExpirationPolicy {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThrottledUseAndTimeoutExpirationPolicy.class);

    private static final long serialVersionUID = 205979491183779408L;

    private long timeToKillInMilliSeconds;

    private long timeInBetweenUsesInMilliSeconds;

    public void setTimeInBetweenUsesInMilliSeconds(final long timeInBetweenUsesInMilliSeconds) {
        this.timeInBetweenUsesInMilliSeconds = timeInBetweenUsesInMilliSeconds;
    }

    public void setTimeToKillInMilliSeconds(final long timeToKillInMilliSeconds) {
        this.timeToKillInMilliSeconds = timeToKillInMilliSeconds;
    }

    @Override
    public boolean isExpired(final TicketState ticketState) {
        if (ticketState.getCountOfUses() == 0 && (System.currentTimeMillis() - ticketState.getLastTimeUsed() < this.timeToKillInMilliSeconds)) {
            LOGGER.debug("Ticket is not expired due to a count of zero and the time being less " + "than the timeToKillInMilliseconds");
            return false;
        }
        if ((System.currentTimeMillis() - ticketState.getLastTimeUsed() >= this.timeToKillInMilliSeconds)) {
            LOGGER.debug("Ticket is expired due to the time being greater than the timeToKillInMilliseconds");
            return true;
        }
        if ((System.currentTimeMillis() - ticketState.getLastTimeUsed() <= this.timeInBetweenUsesInMilliSeconds)) {
            LOGGER.warn("Ticket is expired due to the time being less than the waiting period.");
            return true;
        }
        return false;
    }
}
