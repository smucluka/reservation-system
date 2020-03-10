package hr.fer.opp.projekt.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import hr.fer.opp.projekt.model.TipSmjestJed;

public class TipResultSetExtractor implements ResultSetExtractor<List<TipSmjestJed>> {

	@Override
	public List<TipSmjestJed> extractData(ResultSet rs) throws SQLException, DataAccessException {
		Map<Integer, TipSmjestJed> map = new HashMap<Integer, TipSmjestJed>();
        TipSmjestJed tip = null;
        while (rs.next()) {
        	Integer id = rs.getInt("ID_TIP_SMJEST_JED");
        	tip = map.get(id);
          if(tip == null){
        	  String tipSmjestJed = rs.getString("TIP_SMJEST_JED");
        	  String pogled = rs.getString("POGLED");
        	  Integer kapacitetOd = rs.getInt("KAPACITET_OD");
        	  Integer kapacitetDo = rs.getInt("KAPACITET_DO");
              tip = new TipSmjestJed(id, tipSmjestJed, pogled, kapacitetOd, kapacitetDo);
              map.put(id, tip);
          }
          tip.add(rs.getString("SLIKA"));
        }
        return new ArrayList<TipSmjestJed>(map.values());
	}

}
