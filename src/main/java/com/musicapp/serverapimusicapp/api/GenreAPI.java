package com.musicapp.serverapimusicapp.api;

import com.musicapp.serverapimusicapp.api.output.GenreOutput;
import com.musicapp.serverapimusicapp.dto.GenreDTO;
import com.musicapp.serverapimusicapp.service.IGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class GenreAPI extends BaseAPI{
    @Autowired
    private IGenreService genreService;
    @GetMapping(value = "/genre")
    public GenreOutput showGenre(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "limit", required = false) Integer limit){
        GenreOutput result = new GenreOutput();
        if(page != null && limit != null){
            result.setPage(page);
            Pageable pageable = PageRequest.of(page-1, limit);
            result.setListResult(genreService.findAll(pageable));
            result.setTotalPage((int) Math.ceil((double) (genreService.totalItem())/ limit));
        }else {
            result.setListResult(genreService.findAll());
        }
        return result;
    }
    @PostMapping(value = "/genre")
    public GenreDTO createGenre(@RequestBody GenreDTO model){
        return genreService.save(model);
    }

    @PutMapping(value = "/genre/{id}")
    public GenreDTO updateGenre(@RequestBody GenreDTO model, @PathVariable("id") long id){
        model.setId(id);
        return genreService.save(model);
    }
    @DeleteMapping(value = "/genre")
    public void deleteGenre(@RequestBody long[] ids){
        genreService.delete(ids);
    }
}
