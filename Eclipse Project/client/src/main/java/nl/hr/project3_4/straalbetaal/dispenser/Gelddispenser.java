package nl.hr.project3_4.straalbetaal.dispenser;

public final class Gelddispenser {

	public String currectBalanceInDispenser() {
		return "Tien: " + availableBiljettenVanTien + " Twintig: " + availableBiljettenVanTwintig + " Vijftig: "
				+ availableBiljettenVanVijftig;
	}

	public static void main(String[] args) {
		Gelddispenser dispenser = Gelddispenser.getGelddispenser();

		for (int i = 0; i < 5; i++) {
			if (dispenser.existAskedOption(70)) {
				 // choice 50/100/200

				for (String ii : dispenser.getOptionsForSpecificAmount())
					System.out.println(ii);

				// For snelpinnen this doesn't have to be called!
				//dispenser.removeChosenBillsFromDispenser("a"); // choice
																// "a"/"b"/ "c"

				System.out.println("Choice was: 'a' || To arduino: " + dispenser.getFinalArduinoChoice());

				System.out.println(dispenser.currectBalanceInDispenser());
			}
		}
	}

	private int availableBiljettenVanTien;
	private int availableBiljettenVanTwintig;
	private int availableBiljettenVanVijftig;

	private int finalAmountChoice;
	private String finalArduinoChoice;

	// String with all 9 options
	private String[][] allPossibleOptionsSentToArduino;
	private String[] optionsForSpecificAmount;
	private int[][] finalAmountOpties;

	private static Gelddispenser DISPENSER = new Gelddispenser(5, 5, 5);

	private Gelddispenser(int tien, int twintig, int vijftig) {
		this.availableBiljettenVanTien = tien;
		this.availableBiljettenVanTwintig = twintig;
		this.availableBiljettenVanVijftig = vijftig;
		this.optionsForSpecificAmount = new String[3];
		this.allPossibleOptionsSentToArduino = new String[3][3];
		this.finalAmountOpties = new int[3][3];
	}

	public boolean existAskedOption(int choice) {
		this.finalAmountChoice = choice;
		switch (choice) {
		case 50:
			return existAskedBiljetten(keuzes50());
		case 70:
			boolean b = existAskedBiljetten(snelPinnen70());
			System.out.print(b);
			removeChosenBillsFromDispenser("a");
			return b;
		case 100:
			return existAskedBiljetten(keuzes100());
		case 200:
			return existAskedBiljetten(keuzes200());
		default:
			return false;
		}
	}

	private boolean existAskedBiljetten(int[][] opties) {
		finalAmountOpties = opties;
		int counter = 0;
		for (int i = 0; i < opties.length; i++) {
			if (availableBiljettenVanTien >= opties[i][0] && availableBiljettenVanTwintig >= opties[i][1]
					&& availableBiljettenVanVijftig >= opties[i][2]) {
				System.out.println(opties[i][0] + " " + opties[i][1] + " " + opties[i][2] + "THIS");
				optionsForSpecificAmount[i] = "";
				if (opties[i][0] != 0)
					optionsForSpecificAmount[i] = opties[i][0] + "x G10 ";
				if (opties[i][1] != 0)
					optionsForSpecificAmount[i] += opties[i][1] + "x G20 ";
				if (opties[i][2] != 0)
					optionsForSpecificAmount[i] += opties[i][2] + "x G50 ";
				counter++;
			}
		}
		if (counter == 0) {
			return false;
		}
		return true;
	}

	private int[][] snelPinnen70() {
		int[][] optiesGenerated = new int[3][3];
		optiesGenerated[0][1] = 1;
		optiesGenerated[0][2] = 1;
		allPossibleOptionsSentToArduino[0][0] = "0 1 1";
		return optiesGenerated;
	}

	private int[][] keuzes50() {
		int[][] optiesGenerated = new int[3][3];
		optiesGenerated[0][2] = 1;
		allPossibleOptionsSentToArduino[0][0] = "0 0 1";
		optiesGenerated[1][0] = 1;
		optiesGenerated[1][1] = 2;
		allPossibleOptionsSentToArduino[0][1] = "1 2 0";
		optiesGenerated[2][0] = 5;
		allPossibleOptionsSentToArduino[0][2] = "5 0 0";
		return optiesGenerated;
	}

	private int[][] keuzes100() {
		int[][] optiesGenerated = new int[3][3];
		optiesGenerated[0][0] = 1;
		optiesGenerated[0][1] = 2;
		optiesGenerated[0][2] = 1;
		allPossibleOptionsSentToArduino[1][0] = "1 2 1";
		optiesGenerated[1][1] = 5;
		allPossibleOptionsSentToArduino[1][1] = "0 5 0";
		optiesGenerated[2][2] = 2;
		allPossibleOptionsSentToArduino[1][2] = "0 0 2";
		return optiesGenerated;
	}

	private int[][] keuzes200() {
		int[][] optiesGenerated = new int[3][3];
		optiesGenerated[0][0] = 1;
		optiesGenerated[0][1] = 2;
		optiesGenerated[0][2] = 3;
		allPossibleOptionsSentToArduino[2][0] = "1 2 3";
		optiesGenerated[1][2] = 4;
		allPossibleOptionsSentToArduino[2][1] = "0 0 4";
		optiesGenerated[2][1] = 5;
		optiesGenerated[2][2] = 2;
		allPossibleOptionsSentToArduino[2][2] = "0 5 2";
		return optiesGenerated;
	}

	public void removeChosenBillsFromDispenser(String choice) {
		String[] possibleAnswers = { "a", "b", "c" };

		for (int i = 0; i < possibleAnswers.length; i++) {
			if (choice.equals(possibleAnswers[i])) {
				//System.out.println(availableBiljettenVanTwintig + "Pre");
				availableBiljettenVanTien -= finalAmountOpties[i][0];
				availableBiljettenVanTwintig -= finalAmountOpties[i][1];
				availableBiljettenVanVijftig -= finalAmountOpties[i][2];
				//System.out.println(availableBiljettenVanTwintig + "Post");

				switch (finalAmountChoice) {
				case 50:
					finalArduinoChoice = allPossibleOptionsSentToArduino[0][i];
					break;
				case 70:
					finalArduinoChoice = allPossibleOptionsSentToArduino[0][i];
					break;
				case 100:
					finalArduinoChoice = allPossibleOptionsSentToArduino[1][i];
					break;
				case 200:
					finalArduinoChoice = allPossibleOptionsSentToArduino[2][i];
					break;
				default:
					finalArduinoChoice = "";
				}
			}
		}
	}

	public static Gelddispenser getGelddispenser() {
		return DISPENSER;
	}

	public String[] getOptionsForSpecificAmount() {
		return optionsForSpecificAmount;
	}

	public String getFinalArduinoChoice() {
		return finalArduinoChoice;
	}

}
