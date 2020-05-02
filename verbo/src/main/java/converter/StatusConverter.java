package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("statusConverter")
public class StatusConverter implements Converter<Boolean> {
	//

	@Override
	public Boolean getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
			return (Boolean) uiComponent.getAttributes().get(value);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Boolean value) {
		if (value instanceof Boolean) {
			Boolean entity = (Boolean) value;
			if (entity != null && entity instanceof Boolean && entity.toString() != null) {
				uiComponent.getAttributes().put(entity.toString(), entity);
				return entity.toString();
			}
		}
		return "";
	}

}