package DataSheet;

import java.util.Collection;
import java.util.stream.Stream;

public class DiversityRequestsPool {
    Collection<DiversityRequest> requests;

    public DiversityRequestsPool(Collection<DiversityRequest> requests) {
        this.requests = requests;
    }

    public Stream<DiversityRequest> stream() {
        return requests.stream();
    }

    public Collection<DiversityRequest> getRequests() {
        return requests;
    }
}
