package wdl.api;

import java.util.Map;

/**
 * Represents a WDL mod that register new {@link IWDLMessageType}s.
 */
public interface IMessageTypeAdder extends IWDLMod {
	/**
	 * Gets the various {@link IWDLMessageType}s to register.
	 * 
	 * @return A map of name to type.  
	 */
    Map<String, IWDLMessageType> getMessageTypes();
}
