import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class symbolTester {
    private static final int INITIAL_CAPACITY = 3000;
    private static final int GROWTH_INCREMENT = 100;
    private static final int SHORT_TERM_DAYS = 10;
    private double riskFactor;
    private String symbol;
    private String dataPath;

    private List<Bar> data;
    private List<Trade> trades;
    private boolean loaded = false;

    public symbolTester(String symbol, String dataPath, double risk) {
        this.riskFactor = risk;
        this.symbol = symbol;
        this.dataPath = dataPath;
        this.data = new ArrayList<>(INITIAL_CAPACITY);
        this.trades = new ArrayList<>(GROWTH_INCREMENT);
        this.loaded = false;
    }

    public List<Trade> getTrades() {
        return trades;
    }

    public void loadData() {
        String fileName = dataPath + symbol + "_Daily.csv";
        try (FileReader fr = new FileReader(fileName);
             BufferedReader br = new BufferedReader(fr)) {

            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                data.add(new Bar(line));
            }
            loaded = true;

        } catch (IOException e) {
            System.out.println("Something is wrong: " + e.getMessage());
            loaded = false;
        }
    }

    private boolean xDaysLow(int ind, int days) {
        for (int i = ind - 1; i > ind - days; i--) {
            if (data.get(i).getLow() < data.get(ind).getLow())
                return false;
        }
        return true;
    }

    private boolean xDaysHigh(int ind, int days) {
        for (int i = ind - 1; i > ind - days; i--) {
            if (data.get(i).getHigh() > data.get(ind).getHigh())
                return false;
        }
        return true;
    }

    private void outcomes(Trade T, int ind) {
        for (int i = ind; i < data.size(); i++) {
            if ((T.getDir() == Direction.LONG && data.get(i).getHigh() > T.getTarget())
                    || (T.getDir() == Direction.SHORT && data.get(i).getLow() < T.getTarget())) {
                data.get(i).getOpen();
                float close = (T.getDir() == Direction.LONG) ? T.getTarget() : T.getStopLoss();
                int holdingPeriod = i - ind;
                T.close(data.get(i).getDate(), close, holdingPeriod);
                return;
            }
        }
        // If we get here, the trade is not closed, close it at the close of the last day
        int lastDayIndex = data.size() - 1;
        T.close(data.get(lastDayIndex).getDate(), data.get(lastDayIndex).getClose(), lastDayIndex - ind);
    }

    public boolean test() {
        if (!loaded) {
            loadData();
            if (!loaded) {
                System.out.println("Cannot load data");
                return false;
            }
        }

        for (int i = SHORT_TERM_DAYS; i < data.size() - 1; i++) {
            if (xDaysLow(i, SHORT_TERM_DAYS)
                    && data.get(i).getLow() < data.get(i - 1).getLow()
                    && data.get(i).getHigh() > data.get(i - 1).getHigh()
                    && (data.get(i).getHigh() - data.get(i).getClose()) / (data.get(i).getHigh() - data.get(i).getLow()) < 0.1) {

                float entryPrice = data.get(i + 1).getOpen();
                float stopLoss = data.get(i).getLow() - 0.01f;
                float risk = entryPrice - stopLoss;
                float target = (float) (entryPrice + riskFactor * risk);
                Trade T = new Trade();
                T.open(symbol, data.get(i + 1).getDate(), entryPrice, stopLoss, target, Direction.LONG);
                outcomes(T, i + 1);
                trades.add(T);

            } else if (xDaysHigh(i, SHORT_TERM_DAYS)
                    && data.get(i).getHigh() > data.get(i - 1).getHigh()
                    && data.get(i).getLow() < data.get(i - 1).getLow()
                    && (data.get(i).getClose() - data.get(i).getLow()) / (data.get(i).getHigh() - data.get(i).getLow()) < 0.1) {

                float entryPrice = data.get(i + 1).getOpen();
                float stopLoss = data.get(i).getHigh() + 0.01f;
                float risk = stopLoss - entryPrice;
                float target = (float) (entryPrice - riskFactor * risk);
                Trade T = new Trade();
                T.open(symbol, data.get(i + 1).getDate(), entryPrice, stopLoss, target, Direction.SHORT);
                outcomes(T, i + 1);
                trades.add(T);
            }
        }

        return true;
    }
}
