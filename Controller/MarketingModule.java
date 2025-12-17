package hypermarket;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.Scanner;

public class MarketingModule {

    private List<Offer> activeOffers;
    private final String FILE_NAME = "Offers.txt";

    public MarketingModule() {
        this.activeOffers = new ArrayList<>();
        loadOffersFromFile();
    }

    public void createOffer(int offerId, String description, double discountValue, String validityDateString) {
        LocalDate validUntil = LocalDate.parse(validityDateString);

        Offer newOffer = new Offer(offerId, description, discountValue, validUntil);
        activeOffers.add(newOffer);

        saveOfferToFile(newOffer);
    }

    public boolean sendOfferToInventory(int offerID) {
        Offer offerToSend = null;

        for (Offer offer : activeOffers) {
            if (offer.getOfferID() == offerID) {
                offerToSend = offer;
                break;
            }
        }
        return offerToSend != null;
    }

    public String getReport() {
        StringBuilder report = new StringBuilder();

        report.append("\n--- Marketing Report ---\n");
        report.append("Total Active Offers: ").append(activeOffers.size()).append("\n");
        report.append("Offer Details:\n");

        if (activeOffers.isEmpty()) {
            report.append("No active offers available.\n");
        } else {
            for (Offer offer : activeOffers) {
                report.append("ID: ").append(offer.getOfferID())
                        .append(" | Desc: ").append(offer.getOfferDescription())
                        .append(" | Discount: ").append(offer.getDiscountValue())
                        .append("%")
                        .append(" | Expires: ").append(offer.getExpiryDate())
                        .append("\n");
            }
        }
        report.append("------------------------\n");

        return report.toString();
    }

    public List<Offer> getActiveOffers() {
        return activeOffers;
    }

    private void saveOfferToFile(Offer offer) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true); BufferedWriter bw = new BufferedWriter(fw); PrintWriter out = new PrintWriter(bw)) {

            out.println(offer.getOfferID() + ","
                    + offer.getOfferDescription() + ","
                    + offer.getDiscountValue() + ","
                    + offer.getExpiryDate());

        } catch (IOException e) {
            System.err.println("Error saving offer to file: " + e.getMessage());
        }
    }

    private void loadOffersFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");

                if (parts.length == 4) {
                    try {
                        int id = Integer.parseInt(parts[0].trim());
                        String desc = parts[1].trim();
                        double discount = Double.parseDouble(parts[2].trim());
                        LocalDate date = LocalDate.parse(parts[3].trim());

                        Offer loadedOffer = new Offer(id, desc, discount, date);
                        activeOffers.add(loadedOffer);
                    } catch (Exception e) {

                        System.err.println("Skipping invalid line: " + line);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }

    public boolean deleteOffer(int offerId) {
        Offer offerToRemove = null;

        for (Offer offer : activeOffers) {
            if (offer.getOfferID() == offerId) {
                offerToRemove = offer;
                break;
            }
        }

        if (offerToRemove != null) {
            activeOffers.remove(offerToRemove);
            rewriteOffersToFile();
            return true;
        }
        return false;
    }

    private void rewriteOffersToFile() {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Offer offer : activeOffers) {
                out.println(offer.getOfferID() + ","
                        + offer.getOfferDescription() + ","
                        + offer.getDiscountValue() + ","
                        + offer.getExpiryDate());
            }
        } catch (IOException e) {
            System.err.println("Error updating file: " + e.getMessage());
        }
    }
}
