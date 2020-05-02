package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import model.Estado;

@FacesConverter("estadoConverter")
public class EstadoConverter implements Converter<Object> {
	//

	@Override
	public Estado getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
			return (Estado) uiComponent.getAttributes().get(value);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value instanceof Estado) {
			Estado entity = (Estado) value;
			if (entity != null && entity instanceof Estado && entity.toString() != null) {
				uiComponent.getAttributes().put(entity.toString(), entity);
				return entity.toString();
			}
		}
		return "";
	}

}