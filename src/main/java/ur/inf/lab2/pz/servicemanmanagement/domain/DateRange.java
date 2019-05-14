package ur.inf.lab2.pz.servicemanmanagement.domain;

import java.util.Date;

public class DateRange {
    private Date from;
    private Date to;

    public DateRange(Date from, Date to) {
        if (from == null || to == null)
            throw new IllegalArgumentException();
        this.from = from;
        this.to = to;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }
}
