package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import model.Cidade;

@FacesConverter("cidadeConverter")
public class CidadeConverter implements Converter<Object> {
	//

	@Override
	public Cidade getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
			return (Cidade) uiComponent.getAttributes().get(value);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value != null) {
			if (value instanceof Cidade) {
				Cidade entity = (Cidade) value;
				if (entity != null && entity instanceof Cidade && entity.toString() != null) {
					uiComponent.getAttributes().put(entity.toString(), entity);
					return entity.toString();
				}
			}
			return "";
		}
		return "";
	}

}
