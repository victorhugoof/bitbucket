package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("objetoConverter")
public class ObjetoConverter implements Converter<Object> {
	//

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
			return (Object) uiComponent.getAttributes().get(value);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value instanceof Object) {
			Object entity = (Object) value;
			if (entity != null && entity instanceof Object && entity.toString() != null) {
				uiComponent.getAttributes().put(entity.toString(), entity);
				return entity.toString();
			}
		}
		return "";
	}

}