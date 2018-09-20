package cosc4353;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for creating all continent and territory objects for the "board".
 * Called at the beginning of the game to initialize the "board".
 * @author Tyler Remmie
 * @date 9/8/2018
 */

public class Board {

    private String[] territoriesArray;
    private String[] continentsArray;
    private String[] adjacenciesArray;

	private String territoriesFile = "src\\main\\resources\\territories.txt";
	private String continentsFile = "src\\main\\resources\\continents.txt";
    private String adjacenciesFile = "src\\main\\resources\\adjacent.txt";
    
    private BufferedReader filereader;
    private String inputline;
    private List<String> lines;

    public Board() {

    }

    public void createBoard() {

        try {

            //Read Territories Input
            filereader = new BufferedReader(new FileReader(territoriesFile));
            lines = new ArrayList<String>();

            while ((inputline = filereader.readLine()) != null)
                lines.add(inputline);
            territoriesArray = lines.toArray(new String[]{});

            //Read Adjacency Input
            filereader = new BufferedReader(new FileReader(adjacenciesFile));
            lines = new ArrayList<String>();

            while ((inputline = filereader.readLine()) != null)
                lines.add(inputline);
            adjacenciesArray = lines.toArray(new String[]{});

            //Read Continent Input
            filereader = new BufferedReader(new FileReader(continentsFile));
            lines = new ArrayList<String>();

            while ((inputline = filereader.readLine()) != null)
                lines.add(inputline);
            continentsArray = lines.toArray(new String[]{});


        }   catch (FileNotFoundException error) {
                System.out.println(error.getMessage());
        }   catch (IOException error) {
			    System.out.println(error.getMessage());
		}

    }
    
}