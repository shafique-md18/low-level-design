package splitwise.manager;

import splitwise.model.Group;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GroupManager {
    private final Map<String, Group> groups;

    public GroupManager() {
        this.groups = new HashMap<>();
    }

    public Group addGroup(String name, String description) {
        Group group = new Group(UUID.randomUUID().toString(), name, description);
        groups.put(group.getId(), group);
        return group;
    }

    public Group getGroup(String groupId) {
        return groups.get(groupId);
    }
}
