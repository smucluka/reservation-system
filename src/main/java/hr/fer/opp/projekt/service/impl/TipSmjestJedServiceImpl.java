package hr.fer.opp.projekt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import hr.fer.opp.projekt.dao.SlikeDao;
import hr.fer.opp.projekt.dao.TipSmjestJedDao;
import hr.fer.opp.projekt.model.TipSmjestJed;
import hr.fer.opp.projekt.service.TipSmjestJedService;

@Service
public class TipSmjestJedServiceImpl implements TipSmjestJedService {
	
	@Autowired
	TipSmjestJedDao tipSmjestJedDao;
	
	@Autowired
	SlikeDao slikeDao;
	
	@Autowired
	ResourceLoader resourceLoader;

	@Override
	public void dodajTipSmjestJed(TipSmjestJed tipSmjestJed, MultipartFile[] files)  {
		Integer idTipSmjestJed = tipSmjestJedDao.dodajTipSmjestJed(tipSmjestJed);
		List<String> slikeList = new ArrayList<>();
		
		for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            String filename = tipSmjestJed.getTipSmjestJed() + "_" + tipSmjestJed.getPogled() + "_" + tipSmjestJed.getKapacitetOd() + "_" + tipSmjestJed.getKapacitetDo() + "_" + i + "_" + file.getName();
            slikeList.add(filename);
           
            try {
// 
//                byte[] bytes = file.getBytes();
//                BufferedOutputStream stream =
//                        new BufferedOutputStream(new FileOutputStream(new File(servletContext.getRealPath("/")+"/resources/images/"+filename)));
//                stream.write(bytes);
//                stream.close();
            	
            	file.transferTo(resourceLoader.getResource("resources/images/" + filename).getFile());

                
            } catch (Exception e) {
               //TODO provjeriti
            }
        }
		slikeDao.insertBatch(idTipSmjestJed, slikeList);
	}

	@Override
	public List<TipSmjestJed> getTipSmjestJedList() {
		return tipSmjestJedDao.getTipSmjestJedList();
	}

	@Override
	public void izbrisiTip(Integer idTipSmjestJed) {
		//TODO fizicki izbrisati slike
		tipSmjestJedDao.izbrisiTip(idTipSmjestJed);
		
	}

}
