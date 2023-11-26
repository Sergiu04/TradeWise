package service;

import model.Stock;
import com.opencsv.CSVWriter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.FileWriter;
import java.io.IOException;
import java.io.FileOutputStream;
import java.util.List;

public class ExportService {

    public void exportStocks(List<Stock> stocks, String format) throws IOException {
        if (format.equals("csv")) {
            exportStocksToCSV(stocks);
        } else if (format.equals("pdf")) {
            exportStocksToPDF(stocks);
        } else {
            throw new IllegalArgumentException("Unsupported format: " + format);
        }
    }

    private void exportStocksToCSV(List<Stock> stocks) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter("stocks.csv"))) {
            String[] header = { "ID", "Name", "Purchase Price", "Selling Price", "Automatic Sale Percentage", "Notification Decrease Percentage", "Prediction Gain Percentage" };
            writer.writeNext(header);

            for (Stock stock : stocks) {
                String[] line = { stock.getId().toString(), stock.getName(), stock.getPurchasePrice().toString(), stock.getSellingPrice().toString(), stock.getAutomaticSalePercentage().toString(), stock.getNotificationDecreasePercentage().toString(), stock.getPredictionGainPercentage().toString() };
                writer.writeNext(line);
            }
        }
    }

    private void exportStocksToPDF(List<Stock> stocks) throws IOException {
        XWPFDocument document = new XWPFDocument();
        XWPFTable table = document.createTable();

        XWPFTableRow headerRow = table.getRow(0);
        headerRow.getCell(0).setText("ID");
        headerRow.addNewTableCell().setText("Name");
        headerRow.addNewTableCell().setText("Purchase Price");
        headerRow.addNewTableCell().setText("Selling Price");
        headerRow.addNewTableCell().setText("Automatic Sale Percentage");
        headerRow.addNewTableCell().setText("Notification Decrease Percentage");
        headerRow.addNewTableCell().setText("Prediction Gain Percentage");

        for (Stock stock : stocks) {
            XWPFTableRow row = table.createRow();
            row.getCell(0).setText(stock.getId().toString());
            row.getCell(1).setText(stock.getName());
            row.getCell(2).setText(stock.getPurchasePrice().toString());
            row.getCell(3).setText(stock.getSellingPrice().toString());
            row.getCell(4).setText(stock.getAutomaticSalePercentage().toString());
            row.getCell(5).setText(stock.getNotificationDecreasePercentage().toString());
            row.getCell(6).setText(stock.getPredictionGainPercentage().toString());
        }

        try (FileOutputStream out = new FileOutputStream("stocks.pdf")) {
            document.write(out);
        }
    }
}
