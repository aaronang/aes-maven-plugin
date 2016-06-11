package pt.up.fe.aes.base.events;

import pt.up.fe.aes.base.model.Node.Type;

public interface EventListener {

	void endTransaction (String transactionName, boolean[] activity, boolean isError);
	
	void endTransaction (String transactionName, boolean[] activity, int hashCode, boolean isError);

	void addNode(int id, String name, Type type, int parentId, int startLine, int endLine);

	void addProbe(int id, int nodeId);
	
	void endSession();
}
