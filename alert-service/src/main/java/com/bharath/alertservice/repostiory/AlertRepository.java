package com.bharath.alertservice.repostiory;

import com.bharath.alertservice.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository  extends JpaRepository<Alert, Long> {
}
