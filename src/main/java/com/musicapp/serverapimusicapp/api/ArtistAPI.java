package com.musicapp.serverapimusicapp.api;

import com.musicapp.serverapimusicapp.api.input.ArtistRequest;
import com.musicapp.serverapimusicapp.api.output.ArtistOutput;
import com.musicapp.serverapimusicapp.dto.ArtistDTO;
import com.musicapp.serverapimusicapp.service.IArtistService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class ArtistAPI extends BaseAPI<ArtistDTO>{
    @Autowired
    private IArtistService artistService;
    @GetMapping(value = "/artist")
    public ArtistOutput showArtist(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "limit", required = false) Integer limit){
        ArtistOutput result = new ArtistOutput();
        if(page != null && limit != null){
            result.setPage(page);
            result.setTotalPage((int) Math.ceil((double) (artistService.totalItem())/ limit));
            Pageable pageable = PageRequest.of(page-1, limit);
            result.setListResult(artistService.findAll(pageable));
        }else {
            result.setListResult(artistService.findAll());
        }
        return result;
    }
    @GetMapping(value = "/artist/{id}")
    public ResponseEntity<List<ArtistDTO>> getArtistByID(@PathVariable("id") long id){
        List<ArtistDTO> artistDTOList = artistService.findByIDUser(id);
        if (artistDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(artistDTOList, HttpStatus.OK);
    }

    @PostMapping(value = "/artist")
    public ArtistDTO createArtist(@RequestBody ArtistDTO model){
        return artistService.save(model);
    }
    @PutMapping(value = "/artist/{id}")
    public ArtistDTO updateArtist(@RequestBody ArtistDTO model, @PathVariable("id") long id, HttpServletRequest request){
        System.out.println(id);
        model.setId(id);
        return artistService.save(model);

    }

    @DeleteMapping(value = "/artist")
    public void deleteArtist(@RequestBody long[] ids, HttpServletRequest request){
        artistService.delete(ids);
    }

}
