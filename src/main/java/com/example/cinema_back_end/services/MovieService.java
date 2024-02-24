package com.example.cinema_back_end.services;


import com.example.cinema_back_end.dtos.MovieDTO;
import com.example.cinema_back_end.entities.Movie;
import com.example.cinema_back_end.repositories.IMovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService implements IMovieService{

    @Autowired
    private IMovieRepository  movieRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<MovieDTO> findAllShowingMovies() {
        return movieRepository.findMoviesByIsShowingOrderByIdDesc(1)
                .stream()
                .map(movie -> modelMapper.map(movie, MovieDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public MovieDTO getById(Integer movieId) {
        return modelMapper.map(movieRepository.getById(movieId),MovieDTO.class);
    }

    @Override
    public List<MovieDTO> findAllShowingMoviesByName(String keyword) {
        return movieRepository.findMoviesByIsShowingAndNameContaining(1,keyword)
                .stream().map(movie -> modelMapper.map(movie,MovieDTO.class))
                .collect(Collectors.toList());
    }

	@Override
	public Movie save(Movie movie) {
		try {
			Movie temp = movieRepository.save(movie);
			movieRepository.flush();
			System.out.println(temp);
	        // Chuyển đổi từ Movie sang MovieDTO và trả về
	        return temp;
	    } catch (Exception e) {
	        // Xử lý ngoại lệ nếu có
	        // Có thể log lỗi hoặc thực hiện các hành động khác tùy thuộc vào yêu cầu
	        e.printStackTrace();
	        return null; // hoặc bạn có thể ném ra một ngoại lệ hoặc trả về thông báo lỗi khác
	    }
	}

	@Override
	public void delete(int id) {
		movieRepository.deleteById(id);
	}
}
