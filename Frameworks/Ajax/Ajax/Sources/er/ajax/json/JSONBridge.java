package er.ajax.json;

import org.jabsorb.JSONRPCBridge;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WORequest;
import com.webobjects.appserver.WOResponse;
import com.webobjects.appserver.WOSession;

import er.ajax.json.localarg.WOContextArgResolver;
import er.ajax.json.localarg.WORequestArgResolver;
import er.ajax.json.localarg.WOResponseArgResolver;
import er.ajax.json.localarg.WOSessionArgResolver;
import er.ajax.json.serializer.EOEnterpriseObjectSerializer;
import er.ajax.json.serializer.ERXConstantSerializer;
import er.ajax.json.serializer.NSArraySerializer;
import er.ajax.json.serializer.NSDataSerializer;
import er.ajax.json.serializer.NSDictionarySerializer;
import er.ajax.json.serializer.NSTimestampSerializer;

/**
 * Subclass of JSONRPCBridge.
 * 
 *
 * @author ak
 */
public class JSONBridge extends JSONRPCBridge {

	private static boolean isInitialized;
	
	public static void _initializeBridge() {
		if(isInitialized) {
			return;
		}
		try {
			JSONRPCBridge.registerLocalArgResolver(WOResponse.class, WOResponse.class, new WOResponseArgResolver());
			JSONRPCBridge.registerLocalArgResolver(WORequest.class, WORequest.class, new WORequestArgResolver());
			JSONRPCBridge.registerLocalArgResolver(WOContext.class, WOContext.class, new WOContextArgResolver());
			JSONRPCBridge.registerLocalArgResolver(WOSession.class, WOContext.class, new WOSessionArgResolver());
			JSONRPCBridge.registerLocalArgResolver(JSONRPCBridge.class, WOContext.class, new WOSessionArgResolver());
			JSONRPCBridge.getSerializer().registerSerializer(new EOEnterpriseObjectSerializer());
			JSONRPCBridge.getSerializer().registerSerializer(new NSArraySerializer());
			JSONRPCBridge.getSerializer().registerSerializer(new NSDictionarySerializer());
			JSONRPCBridge.getSerializer().registerSerializer(new NSTimestampSerializer());
			JSONRPCBridge.getSerializer().registerSerializer(new NSDataSerializer());
			JSONRPCBridge.getSerializer().registerSerializer(new ERXConstantSerializer());
			isInitialized = true;
		}
		catch (Exception e) {
			throw new RuntimeException("Failed to initialize JSON.");
		}
	}
	
	/**
	 * Factory to create a json bridge.
	 */
	public static JSONBridge createBridge() {
		_initializeBridge();
		JSONBridge result = new JSONBridge();
		// AK: remove this when we find out why dupes don't work
		boolean value = true;
		JSONBridge.getSerializer().setFixupCircRefs(value);
		JSONBridge.getSerializer().setFixupDuplicates(value);
		return result;
	}
}
