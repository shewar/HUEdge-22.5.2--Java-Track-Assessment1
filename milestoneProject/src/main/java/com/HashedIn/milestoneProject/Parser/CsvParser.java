package com.HashedIn.milestoneProject.Parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.HashedIn.milestoneProject.Entity.Show;
import com.HashedIn.milestoneProject.Exception.ShowNotValid;
import com.HashedIn.milestoneProject.Repository.ShowRepository;


// Parses the csv file and stores the data in arraylist

public class CsvParser {

	
	List<Show> movieList;
	
	public CsvParser() {
		movieList = new ArrayList<>();
	}
	
	public List<Show> getmovies(){
		
		String line = "";
		final String DELIMETER=",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/static/netflix_data.csv"));
			while((line=br.readLine())!=null) {
				try {
					String[] movieValues = line.split(DELIMETER);
					if(movieValues.length!=12) {
						throw new ShowNotValid("Movie data is missing");
					}
					Show show  = new Show(movieValues[0],movieValues[1],movieValues[2],movieValues[3],movieValues[4],movieValues[5],movieValues[6],
							movieValues[7],movieValues[8],movieValues[9],movieValues[10],movieValues[11]);
					
					movieList.add(show);
				}
				catch(ShowNotValid e) {
					System.out.print(e.getMessage());
				}
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
				
			
		return movieList;
			}
		
}
