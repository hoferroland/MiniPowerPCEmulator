package ch.zhaw.info3.miniPowerPCEmu.app;
/**
 * @version 0.1
 * Parse the file and split it up into address, command and parameters.
 *
 */
public class FileParser {
	
    public String[] parseLine(String line) {
        String[] parsedLine = new String[5];
        String[] delim;

        // Remove Comments
        delim = line.split(";");
        String restOfLine = delim[0];

        // Get Address
        delim = line.split(" ");
        parsedLine[0] = delim[0];

        // Get Command
        parsedLine[1] = delim[1];

        // Get Parameters
        if (delim.length > 2) {
            String params = "";
            int i = 2;
            while (i < delim.length) {
                params = params + " " + delim[i];
                i++;
            }
            parsedLine[2] = params;
        }

        // Return line
        return parsedLine;


    }
}
