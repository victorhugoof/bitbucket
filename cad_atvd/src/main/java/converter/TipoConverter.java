package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import model.Tipo;

@FacesConverter("tipoConverter")
public class TipoConverter implements Converter{
	// CLASSE PARA REALIZAR A CONVERSÃO DO TIPO DE ATIVIDADE (UTILIZADA PARA INSERIR NO COMBOBOX)
	
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		 if (value != null && !value.isEmpty()) {
	            return uiComponent.getAttributes().get(value);
	        }
	        return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value instanceof Tipo) {
            Tipo entity= (Tipo) value;
            if (entity instanceof Tipo && entity.toString() != null) {
                uiComponent.getAttributes().put( entity.toString(), entity);
                return entity.toString();
            }
        }
        return "";
	}

}