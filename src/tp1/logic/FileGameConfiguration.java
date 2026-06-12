package tp1.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import tp1.exceptions.*;
import tp1.logic.gameobjects.*;
import tp1.util.MyStringUtils;
import tp1.view.Messages;

public class FileGameConfiguration implements GameConfiguration {
    

    private final int remainingTime; 
    private final int points; 
    private final int lives; 
    

    private final Mario mario; 
    private final List<GameObject> npcs; 

    public FileGameConfiguration(String fileName, GameWorld game) throws GameLoadException {
    	//inicializacion de la lista 
        this.npcs = new ArrayList<>();
        
        // creamos el buffer de lectura: reader
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
        	
        	//extraemos linea a linea 
            String datos = reader.readLine(); 
            if (datos == null) throw new GameLoadException(Messages.EMPTY_FILE); 
            
            //array para guardar las palabras de la linea 
            String[] arrayDatos = MyStringUtils.splitWords(datos); 
            if (arrayDatos.length != 3) throw new GameLoadException(Messages.INVALID_GAME_STATUS.formatted(datos)); 
            
            //parseo de los atributos 
            this.remainingTime = Integer.parseInt(arrayDatos[0]); 
            this.points = Integer.parseInt(arrayDatos[1]);
            this.lives = Integer.parseInt(arrayDatos[2]); 
            
            //conversion de objetos
            Mario auxMario = null;
            
            //mientras que haya lineas...
            while ((datos = reader.readLine()) != null) {
            	
                if (!datos.trim().isEmpty()) {                 
                    String[] objWords = MyStringUtils.splitWords(datos);
                    
                    //comprobamos si es mario parseando el objeto 
                    Mario m = new Mario().parse(objWords, game);
                    if (m != null) {
                        auxMario = m; 
                    } else {
                    	
                    	// si no es Mario es otro 
                        GameObject obj = GameObjectFactory.parse(objWords, game);
                        this.npcs.add(obj);
                    }
                }
            }
            
            //asignamos el mario auxiliar al atributo de la clase 
            this.mario = auxMario;

        } catch (FileNotFoundException fnfe) {  // excepcion porque no existe el archivo  
            
            throw new GameLoadException(Messages.FILE_NOT_FOUND.formatted(fileName), fnfe);
        }catch (GameLoadException e) {  // excepcion porque fallo en la carga 
        	
        	throw e; 
        }catch (GameParseException | OffBoardException e) { // excepcion por fallo en los parametros o en las posiciones 
        	
        	throw new GameLoadException(Messages.INVALID_FILE_CONFIGURATION.formatted(fileName), e);
        }catch (Exception e) {  
        	
        	throw new GameLoadException(Messages.READ_FILE_ERROR.formatted(fileName), e);
        }
    }

    @Override 
    public int getRemainingTime() { return this.remainingTime; }
    
    @Override 
    public int points() { return this.points; }
    
    @Override
    public int numLives() { return this.lives; }


    @Override
    public Mario getMario() { 
    	//devolvemos una copia profunda de mario para evitar modificaciones 
    	//del objeto por paso por referencia
    	if (this.mario != null) { return this.mario.copy(null); }
    	else { return null; }
         
    }

    @Override
    public List<GameObject> getNPCObjects() {     	
    	//devolvemos una copia profunda de los objetos de la lista para evitar modificaciones 
    	//del objeto por paso por referencia
        List<GameObject> npcsCopy = new ArrayList<>();
        for (GameObject obj : this.npcs) {
        	npcsCopy.add(obj.copy(null));
        }
        return npcsCopy; 
    }
}

