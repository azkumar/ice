package main.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

public class TaskOne {

	private static String pathToInputFile = "src/main/resource/CUSIP.txt";
	private static String pathToOutputFile = "src/main/resource/CUSIP_Output.txt";

	public static void main(String[] args) {

		try {
			Map<String, Float> cusIpsWithLatestPrice = fetchLatestPriceOfCUSIPFromFile();
			writeLatestPricesOfCUSIPToFile(cusIpsWithLatestPrice);

		} catch ( IOException e) {
			System.out.println("Error occured while processing the CUSIP file :: " + e.getMessage());
//			e.printStackTrace();
		}
	}

	/**
	 *
	 * @return a map of all the CUSIP with latest price maintaining the order of CUSIP found in the file
	 * @throws IOException
	 */
	private static Map<String, Float> fetchLatestPriceOfCUSIPFromFile() throws IOException {
		Map<String, Float> cusIpsWithLatestPrice = new LinkedHashMap<>();
		BufferedReader reader = Files.newBufferedReader(Paths.get(pathToInputFile), StandardCharsets.UTF_8);
		String line = "";
		String prevCusIp = null;
		String nextCusIp = null;
		Float latestPrice = null;
		while((line = reader.readLine()) != null) {
			if (!line.isEmpty()) {
				try {
					latestPrice = Float.parseFloat(line);
				} catch (NumberFormatException nfe) {
					if (!line.equals("...")) {
						if (null == prevCusIp)
							prevCusIp = line;
						nextCusIp = line;
					}
				}
				if (!prevCusIp.equalsIgnoreCase(nextCusIp)) {
					cusIpsWithLatestPrice.put(prevCusIp, latestPrice);
					prevCusIp = nextCusIp;
				}
			}
		}
		//Add the last price to its cusip
		cusIpsWithLatestPrice.put(prevCusIp, latestPrice);

		return cusIpsWithLatestPrice;
	}

	/**
	 * Write the latest price of the CUSIP to the file
	 * @param cusIpsWithLatestPrice
	 * @throws IOException
	 */
	private static void writeLatestPricesOfCUSIPToFile(Map<String, Float> cusIpsWithLatestPrice) throws IOException {

		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(pathToOutputFile), StandardCharsets.UTF_8)) {
			cusIpsWithLatestPrice.keySet().forEach(key -> {
				System.out.println("Latest price for "+ key + " :: " + cusIpsWithLatestPrice.get(key));
				try {
					writer.write(key + "\n" + cusIpsWithLatestPrice.get(key).toString() + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
	}
}
