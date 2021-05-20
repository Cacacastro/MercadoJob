package com.mercadojob.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mercadojob.db.localidadeDAL;
import com.mercadojob.entity.localidade;



@RestController
public class localidadeController {
	@RequestMapping(value="/api/localidades/listar")	
	public ResponseEntity <Object> listarTodos()
	{
		localidadeDAL dal = new localidadeDAL();
		Map<String,localidade> mappessoas = new HashMap<>();
		List<localidade> todos = dal.getLocalidades("", false);
		for(localidade l : todos)
		   mappessoas.put(""+l.getId(), l);
		return new ResponseEntity<>(mappessoas.values(),HttpStatus.OK);	
	}
	@RequestMapping(value="/api/localidades/apagar")
	public ResponseEntity <Object> apagar(@RequestParam(value="id") int id)
	{
		String retorno="Erro ao apagar";
		localidadeDAL dal = new localidadeDAL();
		if(dal.apagar(id))
			retorno="Apagado com sucesso!";
		return new ResponseEntity<>(retorno,HttpStatus.OK); 
	}
	
    
    @RequestMapping(value="/api/localidades/buscar")	
    public ResponseEntity <Object> buscar(@RequestParam(value="id") int id)
    {   localidade c=null;

    	localidadeDAL dal = new localidadeDAL();
        for (localidade pe : dal.getLocalidades("", false))
             if (pe.getId()==id) c=pe;

        return new ResponseEntity<>(c,HttpStatus.OK);	
    }

    
    @RequestMapping(value="/api/localidades/listarFiltro")	
    public ResponseEntity <Object> listarFiltro(@RequestParam(value="chave") String  
                                  chave, @RequestParam(required=false) String filtro)
    {
      localidadeDAL dal = new localidadeDAL();
      Map<String,localidade> mappessoas = new HashMap<>();
      if(chave.equals("MINHACHAVEVALIDA"))
      {
         List<localidade> todos = dal.getLocalidades("", false);
         for(localidade p : todos)
         if(filtro==null || p.getCidade().toUpperCase().contains(filtro.toUpperCase()))
              mappessoas.put(""+p.getId(), p);
      }
      return new ResponseEntity<>(mappessoas.values(),HttpStatus.OK);		
    }
    
    @RequestMapping(value = "/api/localidade/cadastrar", method = RequestMethod.POST)
    public ResponseEntity<Object> cadPessoa(@RequestBody localidade pessoa) 
    { 
      localidadeDAL dal = new localidadeDAL();
      String retorno="Gravado com sucesso";
      if (pessoa.getId()==0) 
      {
         dal.salvar(pessoa);
      }
      else  //alteração
      {  int i=0;
         retorno="Alterado com sucesso";
         while(dal.getLocalidade(i).getId()!=pessoa.getId()) i++;
         if(i<dal.getLocalidades("", false).size()) dal.alterar(pessoa);
         else retorno="Erro ao alterar";
      }
      return new ResponseEntity<>(retorno,HttpStatus.CREATED);
    }
}