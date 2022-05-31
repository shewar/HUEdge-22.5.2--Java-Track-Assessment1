package com.HashedIn.milestoneProject.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HashedIn.milestoneProject.Entity.Show;

public interface ShowRepository extends JpaRepository<Show, String>{

	List<Show> findByType(String type);
}

