package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import model.GrupoAcesso;

@FacesConverter("acessoConverter")
public class AcessoConverter implements Converter<Object> {
	//

	@Override
	public GrupoAcesso getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
			return (GrupoAcesso) uiComponent.getAttributes().get(value);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value != null) {
			if (value instanceof GrupoAcesso) {
				GrupoAcesso entity = (GrupoAcesso) value;
				if (entity != null && entity instanceof GrupoAcesso && entity.toString() != null) {
					uiComponent.getAttributes().put(entity.toString(), entity);
					return entity.toString();
				}
			}
			return "";
		}
		return "";
	}

}
