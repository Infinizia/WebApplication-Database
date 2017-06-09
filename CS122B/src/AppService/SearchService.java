package AppService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import DbModel.Filters;
import DbModel.Movie;

public class SearchService {
	public SearchService() 
	{
		
	}
	public List<Movie> getMovieList(List<Movie> allMovie, Filters filter)
	{	
		if(filter.genre != null && !filter.genre.trim().isEmpty() && !filter.genre.equals("All")){
			allMovie = allMovie.stream()
					.filter(x -> x.getGenres().stream().anyMatch(y -> y.getName().equals(filter.genre)))
					.collect(Collectors.toList());
		}
		
		switch (filter.type){
			case "start_title":
				allMovie = allMovie.stream()
							.filter(x -> x.getTitle().toLowerCase().startsWith(filter.searchWord.toLowerCase()))
							.collect(Collectors.toList());
				break;
			case "title":
				allMovie = allMovie.stream()
							.filter(x -> x.getTitle().toLowerCase().contains(filter.searchWord.toLowerCase()))
							.collect(Collectors.toList());
				break;
			case "year":
				allMovie = allMovie.stream()
							.filter(x -> Integer.toString(x.getYear()).contains(filter.searchWord))
							.collect(Collectors.toList());;
				break;
			case "director":
				allMovie = allMovie.stream()
							.filter(x -> x.getDirector().toLowerCase().contains(filter.searchWord.toLowerCase()))
							.collect(Collectors.toList());;
				break;
			case "star":
				int index = filter.searchWord.lastIndexOf(" ");
				
				if(index > -1){
					String[] fullName = {filter.searchWord.toLowerCase().substring(0, index), filter.searchWord.toLowerCase().substring(index)};

					allMovie = allMovie.stream().filter(x -> x.getStars().stream()
									.anyMatch(y -> y.first_name.toLowerCase().equals(fullName[0])
										|| y.getLast_name().toLowerCase().equals(fullName[1])
												|| (y.first_name.toLowerCase().equals(fullName[0])
														&& y.getLast_name().toLowerCase().equals(fullName[1])))).collect(Collectors.toList());;
				}
				else{
					allMovie = allMovie.stream().filter(x -> x.getStars().stream()
									.anyMatch(y -> y.first_name.toLowerCase().equals(filter.searchWord) 
										|| y.getLast_name().toLowerCase().equals(filter.searchWord))).collect(Collectors.toList());
				}
				
				break;
	
		}		
		if(filter.orderBy.equals("title")){
			if(filter.isDesc){
				allMovie = allMovie.stream().sorted(Comparator.comparing(Movie::getTitle)).collect(Collectors.toList());
			}
			else{
				allMovie = allMovie.stream().sorted(Comparator.comparing(Movie::getTitle).reversed()).collect(Collectors.toList());
			}
		}
		else{
			if(filter.isDesc){
				allMovie = allMovie.stream().sorted(Comparator.comparing(Movie::getYear).reversed()).collect(Collectors.toList());
			}
			else{
				allMovie = allMovie.stream().sorted(Comparator.comparing(Movie::getYear)).collect(Collectors.toList());
			}
		}
		return allMovie;
	}
	public Movie getSingleMovie(String searchText, String attribute)
	{
		List<Movie> mList = new ArrayList<Movie>();
		Movie movie = new Movie();
		//mList = getMovieList(searchText,attribute);
		if (!mList.isEmpty())
			movie = mList.get(0);
		else
			movie = null;
		return movie;
	}
}
