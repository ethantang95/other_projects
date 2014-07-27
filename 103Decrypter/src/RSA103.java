//This program, and all its associated classes are a proprietary of Ethan, Yuchen Tang.
//By viewing it as an user, you have the right to observe and comment on the code
//You are not allowed to reproduce or claim ownership of the code
//This code, along with Input and SimpleTools class which will be provided are a proprietary of Ethan, Yuchen Tang.
//
//The performance of this program can be measured at a 1 second duration for encryption with a 32 bit key on a
//0.5MB file on a computer running Intel i5-4670k @ 3.8 ghz. This was made possible by the Elsa/Anna Algorithm
//which is an object based repeated squaring method.
//
//This code is in version Alpha 1.0

import java.math.BigInteger;

class RSA103 {

	public static void main(String[] args) {
		run();
	}

	// main menu of the program. option 6 and 7 involving signatures are WIP
	public static void run() {
		choiceLadder(5);
	}

	// separates the option input and goes to the appropriate method
	public static void choiceLadder(int choice) {
		switch (choice) {
		case 5:
			decrypt();
			break;
		default:
			return;
		}
	}

	// decryption works with only asking the user for a file in the general case
	// It looks to see if the user has the decryption key for the file, which is
	// the name that was enscribed through the encrypt method
	public static void decrypt() {

		String[] file = SimpleTools.fileInput("103Bonus.txt");
		if (file == null) {
			String[] message = { "file not found, remember to name the file you want to decrypt as '103Bonus.txt'" };
			printMessage(message);
			return;
		}

		xavierSequence(file);
	}

	// method made to take a file and decodes it according the 103 bonus
	// assignment
	public static void xavierSequence(String[] file) {
		String[] data = SimpleTools.stringSplitter(file[0], ' ');
		data = SimpleTools.splitPointRemover(data, ' ');

		// in his sequence, the first set of numbers is the oKey, the second is
		// the public key to decrypt
		// xavier's key is hardcoded into this program
		String keyInfo = (data[0] + ":" + data[1] + ":0");
		RSAKeys103 userKey = new RSAKeys103(keyInfo);
		RSAKeys103 xavierKey = new RSAKeys103("3125033603:52741219:2387285179");

		String[] toDecrypt = new String[1];
		toDecrypt[0] = "";
		// extracts the actual encrypted part
		for (int i = 0; i < data.length - 3; i++) {
			toDecrypt[0] = (toDecrypt[0] + data[i + 3] + " ");
		}
		toDecrypt[0] = toDecrypt[0].substring(0, toDecrypt[0].length() - 1);
		System.out.println(toDecrypt[0]);
		// first part is decrypt the file with xavier's key
		String[] firstDecryptNum = elsa(decryptConvert(toDecrypt),
				xavierKey.oKey, xavierKey.privKey);

		// at this point, we need to obtain what was written by the user with
		// his key
		String[] secondDecryptNum = elsa(decryptConvert(firstDecryptNum),
				userKey.oKey, userKey.pubKey);
		System.out.println("Original Sequence");
		System.out.println(secondDecryptNum[0]);
		String[] decrypted = convertToChar(secondDecryptNum);

		printMessage(decrypted);
	}

	// converts the file for decrypting... really it's more like parsing
	// converts it into a 2d array of big ints for elsa
	public static BigInteger[][] decryptConvert(String[] toConvert) {
		BigInteger[][] toReturn = new BigInteger[toConvert.length][];
		for (int i = 0; i < toConvert.length; i++) {
			String[] numList = SimpleTools.stringSplitter(toConvert[i], ' ');
			numList = SimpleTools.splitPointRemover(numList, ' ');
			toReturn[i] = new BigInteger[numList.length];
			for (int j = 0; j < numList.length; j++) {
				// System.out.println(numList[j]);
				toReturn[i][j] = new BigInteger("0" + numList[j]);
			}
		}
		return toReturn;
	}

	// converts the numbers into char
	public static String[] convertToChar(String[] toConvert) {
		// with this algorithem, what elsa would return is a string with each 4
		// set separated by a space.
		String[] toReturn = new String[toConvert.length];
		for (int i = 0; i < toConvert.length; i++) {
			String[] numList = SimpleTools.stringSplitter(toConvert[i], ' ',
					false);
			numList = SimpleTools.splitPointRemover(numList, ' ');
			char[] converted = new char[numList.length * 4];
			for (int j = 0; j < numList.length; j++) {
				// set would be the sequence number that was hopfully the
				// original
				long set = 0;
				// just incase there's a null somewhere at the end of the list,
				// really bad code practice though since catch does nothing
				try {
					set = Long.parseLong(numList[j]);
				} catch (Exception e) {
				}
				long first, second, third, fourth;
				if (set != 0) {
					// uses the method we learned in 124 to convert
					fourth = set % 256;
					set = set / 256;
					third = set % 256;
					set = set / 256;
					second = set % 256;
					set = set / 256;
					first = set;
					converted[(4 * j)] = (char) first;
					converted[(4 * j) + 1] = (char) second;
					converted[(4 * j) + 2] = (char) third;
					converted[(4 * j) + 3] = (char) fourth;
				}
			}
			toReturn[i] = new String(converted);
		}
		return toReturn;
	}

	// Elsa and Anna are the main algorithms to converting the string
	// if there's any huge logical errors... it is probably these two
	// elsa mainly takes in integers and formats it to the proper form, it then
	// sends the values needed to anna to do the
	// real work
	public static String[] elsa(BigInteger toUse[][], BigInteger mod,
			BigInteger power) {
		String[] toReturn = new String[toUse.length];
		for (int i = 0; i < toUse.length; i++) {
			// !! possible bug causer if the file somehow is empty
			toReturn[i] = "";
			for (int j = 0; j < toUse[i].length; j++) {
				toReturn[i] = toReturn[i] + anna(toUse[i][j], mod, power) + " ";
			}
			// this is to trim the extra space at the end of the string which
			// caused many errors
			if (toReturn[i].length() > 0) {
				toReturn[i] = toReturn[i]
						.substring(0, toReturn[i].length() - 1);
			}
		}
		return toReturn;
	}

	// this algorithm goes from bottom to up with power method, then goes from
	// up to down again with congruences
	// it first calculates the congruence at every 2 power, then combines the
	// original power as numbers in the 2 power
	// and calculates congruences there
	// this algorithm uses objects in an array to hold the values. much better
	// for sequencing
	public static BigInteger anna(BigInteger base, BigInteger mod,
			BigInteger power) {
		return base.modPow(power, mod);
	}

	public static void printMessage(String[] message) {
		SimpleTools.fileOutput(message, "result.txt");
	}
}

class RSAKeys103 {

	String name;
	BigInteger pubKey, privKey, oKey;
	boolean valid = true;

	RSAKeys103(String toParse) {
		// string should be written in the case oKey:pubKey:privKey
		// this constructor is for if a key was previously specified
		String[] splitted = SimpleTools.stringSplitter(toParse, ':');
		splitted = SimpleTools.splitPointRemover(splitted, ':');
		this.oKey = new BigInteger("" + splitted[0]);
		this.pubKey = new BigInteger("" + splitted[1]);
		this.privKey = new BigInteger("" + splitted[2]);
		this.name = splitted[0].substring(0, 3);
	}

	public void print() {
		if (this.valid)
			System.out.println("Key: " + this.name.toString()
					+ ", Public key: " + this.pubKey.toString()
					+ ", Private key: " + this.privKey.toString()
					+ ", Modulo: " + this.oKey.toString());
	}
}

// PowerSegments are the objects used to keep track of the 2 power congruences
// used by anna
class PowerSegments103 {

	long power;
	BigInteger congruence;

	PowerSegments103(long power, BigInteger congruence) {
		this.power = power;
		this.congruence = congruence;
		// System.out.println ("at power " + power + ", congruence is " +
		// congruence.toString());
	}
}