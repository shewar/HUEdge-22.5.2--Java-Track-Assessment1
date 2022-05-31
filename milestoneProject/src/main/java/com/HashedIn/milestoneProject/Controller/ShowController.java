package com.HashedIn.milestoneProject.Controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.HashedIn.milestoneProject.Entity.Show;
import com.HashedIn.milestoneProject.Service.ShowService;

@RestController
public class ShowController {
	
	@Autowired
	private ShowService showService;
	List<Show> showsList;
	
	
	//controller for fetching data either from database or csv file
	@GetMapping(value = "/{datasource}/{type}")
	public List<Show> searchShows(@PathVariable("datasource") String datasource ,@PathVariable("type") String type , @RequestParam int count,
			@RequestParam(required = false) String listedIn,@RequestParam(required = false) String country,@RequestParam(required = false) String startDate , 
			@RequestParam(required = false) String endDate ,HttpServletResponse response) {
		long duration,startTime,endTime;

		if(datasource.equals("csv")) {
			startTime = System.nanoTime();	
			showsList = showService.searchShowsInCsv(type, count, listedIn,country,startDate, endDate);
			endTime = System.nanoTime();
			duration = (endTime - startTime)/1000000 ;
		}
		
		else {
			startTime = System.nanoTime();	
			showsList = showService.searchShowsInDb(type, count, listedIn,country,startDate, endDate);
			endTime = System.nanoTime();
			duration = (endTime - startTime)/1000000 ;
			System.out.println(showsList);
			
		}
		
		//add total reponse time of api in reponse header
		response.setHeader("X-TIME-TO-EXECUTE", String.valueOf(duration+"ms"));
		if(showsList.size()==0) {
			System.out.println("No data is present for the given query");
		}
		return showsList;
}
	//controller for adding data into database
	@PostMapping(value = "/shows")
	public void addShow(@RequestBody Show show) {
		//showService.addShow(new Show("12","afa","age","sgsg","dhdh","rhrh","gsgs","weher","gsh","hthr","enhtr","trhr"));
		showService.addShow(show);
	}
	
}
