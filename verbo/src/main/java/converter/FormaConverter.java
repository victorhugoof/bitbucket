package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import model.FormaPagamento;

@FacesConverter("formaConverter")
public class FormaConverter implements Converter<Object> {
	//

	@Override
	public FormaPagamento getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
			return (FormaPagamento) uiComponent.getAttributes().get(value);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value instanceof FormaPagamento) {
			FormaPagamento entity = (FormaPagamento) value;
			if (entity != null && entity instanceof FormaPagamento && entity.toString() != null) {
				uiComponent.getAttributes().put(entity.toString(), entity);
				return entity.toString();
			}
		}
		return "";
	}

}