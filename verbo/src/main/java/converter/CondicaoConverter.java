package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import model.CondicaoPagamento;

@FacesConverter("condicaoConverter")
public class CondicaoConverter implements Converter<Object> {
	//

	@Override
	public CondicaoPagamento getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
			return (CondicaoPagamento) uiComponent.getAttributes().get(value);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value instanceof CondicaoPagamento) {
			CondicaoPagamento entity = (CondicaoPagamento) value;
			if (entity != null && entity instanceof CondicaoPagamento && entity.toString() != null) {
				uiComponent.getAttributes().put(entity.toString(), entity);
				return entity.toString();
			}
		}
		return "";
	}

}