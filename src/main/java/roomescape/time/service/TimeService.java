package roomescape.time.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.common.exception.NoSuchElementToDeleteException;
import roomescape.time.dto.TimeCreationRequest;
import roomescape.time.dto.TimeResponse;
import roomescape.time.model.Time;
import roomescape.time.model.TimeManager;
import roomescape.time.repository.TimeQueryingDAO;
import roomescape.time.repository.TimeUpdatingDAO;

import java.util.List;

@Service
public class TimeService {

    private final TimeQueryingDAO timeQueryingDAO;
    private final TimeUpdatingDAO timeUpdatingDAO;

    @Autowired
    public TimeService(TimeQueryingDAO timeQueryingDAO, TimeUpdatingDAO timeUpdatingDAO) {
        this.timeQueryingDAO = timeQueryingDAO;
        this.timeUpdatingDAO = timeUpdatingDAO;
    }

    public List<TimeResponse> getTimeList() {
        List<Time> timeList = timeQueryingDAO.findAllTime();

        return timeList.stream().map(TimeResponse::from).toList();
    }

    public TimeResponse addTime(TimeCreationRequest request) {
        List<Time> timeList = timeQueryingDAO.findAllTime();
        TimeManager timeManager = new TimeManager(timeList);

        Time newTime = TimeCreationRequest.toEntityFrom(request);

        timeManager.validateTime(newTime);
        Long id = timeUpdatingDAO.insertWithKeyHolder(newTime);
        newTime.setId(id);

        return TimeResponse.from(newTime);
    }

    public void deleteTime(Long id) {
        Time time = timeQueryingDAO.findTimeById(id)
                .orElseThrow(() -> new NoSuchElementToDeleteException("There is no reservation with id " + id));
        timeUpdatingDAO.delete(time);
    }
}
