# WebHooks for Bamboo 

Notify external services about build and deployment status with webhooks.

*Supported events:*
 * Build Start / Finish
 * Stage Start / Finish
 * Job Start / Finish
 * Deployment Start / Finish
 * Version created
 
Additionally service can sign requests with HMAC-SHA256 using configurable secret per project.

## Configuration

### Basic settings (same url for all events)
![Screenshot](https://raw.githubusercontent.com/redfox-tools/bamboo-webhooks/master/src/main/resources/images/webhooks/basic.png)

### Advanced settings
![Screenshot](https://raw.githubusercontent.com/redfox-tools/bamboo-webhooks/master/src/main/resources/images/webhooks/advanced.png)

## Events

![Screenshot](https://raw.githubusercontent.com/redfox-tools/bamboo-webhooks/master/src/main/resources/images/webhooks/request.png)

### Build started

`X-Bamboo-Event-Type: BuildStarted`

```json
{
  "id" : "5d342e2d-6a23-4b6b-8698-ddbda9022262",
  "time" : "2019-07-06T21:24:02.125Z",
  "plan" : {
    "name" : "xx - yy",
    "key" : "XX-YY",
    "url" : "http://bamboo.local/bamboo/browse/XX-YY"
  },
  "build" : {
    "key" : "XX-YY-95",
    "number" : 95,
    "trigger" : "Manual build",
    "url" : "http://bamboo.local/bamboo/browse/XX-YY-95",
    "status" : "IN PROGRESS",
    "custom_build" : false,
    "branch_build" : false
  },
  "project_name" : "xx"
}
``` 


### Stage started

`X-Bamboo-Event-Type: StageStarted`

```json
{
  "id" : "f70896ff-a643-4dae-82ff-ff87080f8c4b",
  "time" : "2019-07-06T21:24:03.013Z",
  "plan" : {
    "name" : "xx - yy",
    "key" : "XX-YY",
    "url" : "http://bamboo.local/bamboo/browse/XX-YY"
  },
  "build" : {
    "key" : "XX-YY-95",
    "number" : 95,
    "trigger" : "Manual build",
    "url" : "http://bamboo.local/bamboo/browse/XX-YY-95",
    "status" : "IN PROGRESS",
    "stage" : {
      "name" : "Default Stage"
    },
    "custom_build" : false,
    "branch_build" : false
  },
  "project_name" : "xx"
}
``` 

### Job started

`X-Bamboo-Event-Type: JobStarted`

```json
{
  "id" : "72ad5f00-cbdc-467d-9c51-c9c86d567ddb",
  "time" : "2019-07-06T21:24:03.398Z",
  "plan" : {
    "name" : "xx - yy",
    "key" : "XX-YY",
    "url" : "http://bamboo.local/bamboo/browse/XX-YY"
  },
  "build" : {
    "key" : "XX-YY-95",
    "number" : 95,
    "trigger" : "Manual build",
    "url" : "http://bamboo.local/bamboo/browse/XX-YY-95",
    "status" : "IN PROGRESS",
    "stage" : {
      "name" : "Default Stage",
      "job" : {
        "name" : "Default Job",
        "url" : "http://bamboo.local/bamboo/browse/XX-YY-JOB1-95",
        "status" : "STARTED"
      }
    },
    "custom_build" : false,
    "branch_build" : false
  },
  "project_name" : "xx"
}
``` 

### Job finished

`X-Bamboo-Event-Type: JobFinished`

```json
{
  "id" : "0e8c6ad4-949e-4414-881a-77c3607ff72c",
  "time" : "2019-07-06T21:24:07.817Z",
  "plan" : {
    "name" : "xx - yy",
    "key" : "XX-YY",
    "url" : "http://bamboo.local/bamboo/browse/XX-YY"
  },
  "build" : {
    "key" : "XX-YY-95",
    "number" : 95,
    "trigger" : "Manual build",
    "url" : "http://bamboo.local/bamboo/browse/XX-YY-95",
    "status" : "IN PROGRESS",
    "stage" : {
      "name" : "Default Stage",
      "job" : {
        "name" : "Default Job",
        "url" : "http://bamboo.local/bamboo/browse/XX-YY-JOB1-95",
        "status" : "SUCCESS",
        "duration" : "0:00:02",
        "summary" : "No tests found"
      }
    },
    "custom_build" : false,
    "branch_build" : false
  },
  "project_name" : "xx"
}
``` 

### Stage finished

`X-Bamboo-Event-Type: StageFinished`

```json
{
  "id" : "d317c844-1471-4424-ae69-4d740251553b",
  "time" : "2019-07-06T21:24:17.791Z",
  "plan" : {
    "name" : "xx - yy",
    "key" : "XX-YY",
    "url" : "http://bamboo.local/bamboo/browse/XX-YY"
  },
  "build" : {
    "key" : "XX-YY-95",
    "number" : 95,
    "trigger" : "Manual build",
    "url" : "http://bamboo.local/bamboo/browse/XX-YY-95",
    "status" : "IN PROGRESS",
    "stage" : {
      "name" : "Second stage",
      "jobs" : [ {
        "name" : "second-1",
        "url" : "http://bamboo.local/bamboo/browse/XX-YY-SEC1-95",
        "status" : "SUCCESS",
        "duration" : "0:00:03",
        "summary" : "No tests found"
      }, {
        "name" : "second-2",
        "url" : "http://bamboo.local/bamboo/browse/XX-YY-SEC2-95",
        "status" : "SUCCESS",
        "duration" : "0:00:02",
        "summary" : "No tests found"
      } ]
    },
    "custom_build" : false,
    "branch_build" : false
  },
  "project_name" : "xx"
}
``` 

### Build finished

`X-Bamboo-Event-Type: BuildFinished`

```json
{
  "id" : "de4bba92-7dc2-4d78-a574-725072c7518c",
  "time" : "2019-07-06T21:24:18.115Z",
  "plan" : {
    "name" : "yy",
    "key" : "XX-YY",
    "url" : "http://bamboo.local/bamboo/browse/XX-YY"
  },
  "build" : {
    "key" : "XX-YY-95",
    "number" : 95,
    "trigger" : "Manual build",
    "url" : "http://bamboo.local/bamboo/browse/XX-YY-95",
    "status" : "SUCCESS",
    "summary" : "Manual build",
    "stages" : [ {
      "name" : "Default Stage",
      "jobs" : [ {
        "name" : "Default Job",
        "url" : "http://bamboo.local/bamboo/browse/XX-YY-JOB1-95",
        "status" : "SUCCESS",
        "duration" : "0:00:02",
        "summary" : "No tests found"
      } ]
    }, {
      "name" : "Second stage",
      "jobs" : [ {
        "name" : "second-1",
        "url" : "http://bamboo.local/bamboo/browse/XX-YY-SEC1-95",
        "status" : "SUCCESS",
        "duration" : "0:00:03",
        "summary" : "No tests found"
      }, {
        "name" : "second-2",
        "url" : "http://bamboo.local/bamboo/browse/XX-YY-SEC2-95",
        "status" : "SUCCESS",
        "duration" : "0:00:02",
        "summary" : "No tests found"
      } ]
    } ],
    "custom_build" : false,
    "branch_build" : false
  },
  "project_name" : "xx"
}
```

### Version created

`X-Bamboo-Event-Type: VersionCreated`

```json
{
  "id" : "62ae81a8-567f-4ef6-adb8-1602254fc8f1",
  "time" : "2019-07-06T21:43:40.195Z",
  "plan" : {
    "name" : "yy",
    "key" : "XX-YY",
    "url" : "http://localhost:6990/bamboo/browse/XX-YY"
  },
  "build" : {
    "key" : "XX-YY-95",
    "number" : 95,
    "trigger" : "Manual build",
    "url" : "http://localhost:6990/bamboo/browse/XX-YY-95",
    "status" : "SUCCESS",
    "custom_build" : false,
    "branch_build" : false
  },
  "project_name" : "xx",
  "name" : "release-18",
  "created_by" : "admin"
}
```

### Deployment started

`X-Bamboo-Event-Type: DeploymentStarted`

```json
{
  "id" : "6c3c5702-a260-4734-a03a-7d2606c15e3a",
  "time" : "2019-07-06T21:26:54.583Z",
  "plan" : {
    "name" : "yy",
    "key" : "XX-YY",
    "url" : "http://bamboo.local/bamboo/browse/XX-YY"
  },
  "build" : {
    "key" : "XX-YY-95",
    "number" : 95,
    "trigger" : "Manual build",
    "url" : "http://bamboo.local/bamboo/browse/XX-YY-95",
    "status" : "SUCCESS",
    "custom_build" : false,
    "branch_build" : false
  },
  "project_name" : "xx - yy",
  "version" : "release-15",
  "environment" : "test"
}
``` 

### Deployment finished

`X-Bamboo-Event-Type: DeploymentFinished`

```json
{
  "id" : "ae93386d-5317-4c02-9e4b-02a5e2fd3c2f",
  "time" : "2019-07-06T21:25:34.524Z",
  "plan" : {
    "name" : "yy",
    "key" : "XX-YY",
    "url" : "http://bamboo.local/bamboo/browse/XX-YY"
  },
  "build" : {
    "key" : "XX-YY-95",
    "number" : 95,
    "trigger" : "Manual build",
    "url" : "http://bamboo.local/bamboo/browse/XX-YY-95",
    "status" : "SUCCESS",
    "custom_build" : false,
    "branch_build" : false
  },
  "project_name" : "xx - yy",
  "version" : "release-15",
  "environment" : "test",
  "status" : "SUCCESSFUL"
}
``` 
 
