package com.example.brenda.jccexample.parser;

import com.example.brenda.jccexample.pojo.DatoInteres;

import org.json.JSONObject;

import org.json.JSONException;

public class DatoInteresParser {

	// Los parsers se encargan de convertir un objet Java a JSON y viceversa. (o como se escriba)
	
	public static JSONObject serializeDatoInteres(DatoInteres datoInteres){
		JSONObject json;
		try{
			json = new JSONObject();
			json.put("idDatoInteres", datoInteres.getIdDatoInteres());
			json.put("DatoInteres", datoInteres.getDatoInteres());
		}catch(JSONException e){
			e.printStackTrace();
			json = null;
		}
		return json;
	}
	
	public static DatoInteres parseDatoInteres(JSONObject json){
		DatoInteres datoInteres;
		try{
			datoInteres = new DatoInteres();
			datoInteres.setIdDatoInteres(json.getString("idDatoInteres"));
			datoInteres.setDatoInteres(json.getString("DatoInteres"));
		}catch(JSONException e){
			e.printStackTrace();
			datoInteres = null;
		}
		return datoInteres;
	}
}
