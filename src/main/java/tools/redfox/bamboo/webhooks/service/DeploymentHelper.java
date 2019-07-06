package tools.redfox.bamboo.webhooks.service;

import com.atlassian.bamboo.deployments.versions.service.DeploymentVersionService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;

public class DeploymentHelper {
    public DeploymentHelper(@ComponentImport DeploymentVersionService deploymentVersionService) {
    }
}
