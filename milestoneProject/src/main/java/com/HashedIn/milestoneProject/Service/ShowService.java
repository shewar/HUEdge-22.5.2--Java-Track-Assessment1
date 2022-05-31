package com.HashedIn.milestoneProject.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HashedIn.milestoneProject.Entity.Show;
import com.HashedIn.milestoneProject.Parser.CsvParser;
import com.HashedIn.milestoneProject.Repository.ShowRepository;

@Service
public class ShowService {
	
	List<Show> showList;
	
//	shows data will be stored in arraylist when application loads
	CsvParser parser;
	ShowService(){
		this.parser = new CsvParser();
		showList = parser.getmovies();
	}
	
	@Autowired
	private ShowRepository repository;
	
	//filter function to filter data
	public List<Show> filterShowsList(String type, int count, String listedIn,String country,String startDate, String endDate , List<Show> showsList){
		Stream<Show> shows;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[d-MMM-yy][ MMMM dd, yyyy][MMMM d, yyyy]");
		shows = showsList.stream().filter(show->show.getType().toLowerCase().equals(type.toLowerCase()));
		if(listedIn!=null) {
			shows = shows.filter(show->show.getListed_in().toLowerCase().contains(listedIn.toLowerCase()));
		}
		if(country!=null) {
			shows = shows.filter(show->show.getCountry().toLowerCase().equals(country.toLowerCase()));
		}
		if(startDate!=null) {
			LocalDate start = LocalDate.parse(startDate.strip(),formatter);
			shows = shows.filter(show -> {                           
			                    return show.getDate_added().length()>0 && 
			                    		LocalDate.parse(show.getDate_added().strip().replaceAll("\"", "").strip(),formatter).isAfter(start);
			                });
		}
		if(endDate!=null) {
			LocalDate end = LocalDate.parse(endDate.strip(),formatter);
			shows = shows
	                .filter(show -> {                           
	                    return show.getDate_added().length()>0 && 
	                    		LocalDate.parse(show.getDate_added().strip().replaceAll("\"", "").strip(),formatter).isBefore(end);
	                });
			
		}
		if(count!=0) {
			shows = shows.limit(count);
		}
		return shows.collect(Collectors.toList());
		
	}
	
//	Data will be filtered by type, listedIn , country startDate and endDate 
	public List<Show> searchShowsInCsv(String type, int count, String listedIn,String country,String startDate, String endDate){
		
		List<Show> filteredShowsList = filterShowsList(type, count, listedIn, country, startDate, endDate, showList);
		return filteredShowsList;
		
	}
	
//	Data will be fetched from database based on query parameters like type , country , listedIn etc.
	
	public List<Show> searchShowsInDb(String type, int count, String listedIn,String country,String startDate, String endDate){
		
		List<Show> dbshowsList = repository.findByType(type);
		List<Show> filteredShowsList = filterShowsList(type, count, listedIn, country, startDate, endDate, dbshowsList);
		return filteredShowsList;
			
	}
	
	//it will check if data in csv is in sync with database. If in sync then add row in database else it will make it in sync then will add data
	public void addShow(Show show) {
		int showsCountInDb = (int)repository.count();
		showList = parser.getmovies();
		if(showsCountInDb!=showList.size()) {
			for(Show show1:showList) {
				if(repository.equals(show1)) {
					continue;
				}
				else {
					repository.save(show1);
				}
			}
		}
		repository.save(show);
		
	}
	
	
	
}
