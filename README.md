RLD-FITNESSE-DEMO
=================

A demo project to show how you can use Specification by Example and the tool FitNesse to work in a test-driven way, starting from functional tests in the form of FitNesse examples.

The basic idea of the demo is to implement the `RecordService.addRecordEntry` method for an Electronic Health Records system. Please note that the requirements are very simplified and not realistic; the purpose of the demo is to show how you can go from requirements to FitNesse examples to working code.

Running the Demo
----------------

You run demo by starting the FitNesse wiki by giving the following command from the root directory of the `rld-fitnesse-demo` project:

    mvn -P fitnesse-wiki verify

Open a web browser at <http://localhost:8181/FitNesseDemo>. There is a test suite called `Electronic Health Record` that currently only contains one test page, `AddingInformation`. Navigate to the `AddingInformation` page, which currently only contains a header. Press the `Edit` button to make changes to the page.

In your favorite IDE, open the `rld-fitnesse-demo` project. Any changes you make to the code should be immediately visible when you run the FitNesse tests by pressing the `Test` button in the FitNesse web page.

In our example, we start by adding the following to the FitNesse page `AddingInformation` to verify that it is possible to add and retrieve a record entry:

    !|Add Record Entry|
    |userId  |patientId|entry|
    |drJekyll|4711     |This guy is sick|

    !|Verify Record Entries|
    |patientId|index|entry?|
    |4711     |0    |This guy is sick|

When we try to run this, we get an `IllegalArgumentException` saying that the record for patient with ID 4711 is null. The reason is that we haven't coded the `addRecordEntry` method yet, so we add the following to the class `RecordService`:

    @Autowired
    private PatientRepository patientRepository;

	public void addRecordEntry(User user, Patient patient, String entry) {
        RecordEntry recordEntry = new RecordEntry(patient.record(), entry);
        patient.record().addEntry(recordEntry);
        patientRepository.save(patient);
	}

When we now run the FitNesse test, we get a `NullPointerException` in the `addRecordEntry` method. We realize that this is not a very friendly error message, so we add the following to the start of the method:

        if (user == null || patient == null || entry == null) {
            throw new IllegalArgumentException(
                    "Arguments must not be null: user=" + user + ", patient=" + patient + ", entry=" + entry);
        }

We run the test again and see the improved error message:

    IllegalArgumentException: Arguments must not be null: user=null, patient=null, entry=This guy is sick

Aha! We have not added any users or patients. We add the following to the start of the FitNesse page `AddingInformation`:

    !|Add User|
    |userId    |role names|
    |drJekyll  |DOCTOR|

    !|Add Patient|
    |patientId|first name|last name|
    |4711     |Patient   |Zero     |

We now get further when we run the test, now it fails when it tries to verify the record entries. Obviously, we need to implement the `findRecordForPatient` method, so we add the following to the class `RecordService`:

    public Record findRecordForPatient(String patientId) {
        return patientRepository.findByPatientId(patientId).record();
    }

We run the test again. Success!

Next, we want to add some authorization to the `addRecordEntry` method so that only doctors can add records while nurses cannot. (This is probably a completely nonsensical requirement in a real system.)

To create a FitNesse test for this, we could create another user that is a nurse, and have that user try to add a record. This should fail with some nice error message, and the entry should not be added. To verify that the entry was not added, we could have the doctor add another record entry for the same patient, and then check that the two entries from the doctor are available, while the entry from the nurse is not.

While we're at it, we could improve the `Add Record Entry` FitNesse table to also verify that the error message we get when a nurse tries to add a record is correct. In total, the FitNesse page `AddingInformation` now looks like this:

    !1 Adding Information to an Electronic Health Record

    !|Add User|
    |userId    |role names|
    |drJekyll  |DOCTOR|
    |nurseJacky|NURSE|

    !|Add Patient|
    |patientId|first name|last name|
    |4711     |Patient   |Zero     |

    !|Add Record Entry|
    |userId  |patientId|entry|error message?|
    |drJekyll|4711     |This guy is sick||
    |nurseJacky|4711   |Can I get away with this?|Only doctors can add records|
    |drJekyll|4711     |No, his faking||

    !|Verify Record Entries|
    |patientId|index|entry?|
    |4711     |0    |This guy is sick|
    |4711     |1    |No, his faking  |

When we now run the FitNesse tests, we get 2 errors. The first is that adding a record entry does not give an error message for a nurse, and the other that the second entry retrieved when verifying the entries is the one from the nurse.

We fix the problems by first adding the following at the top of the `addRecordEntry` method:

        if (!user.hasRole("DOCTOR")) {
            throw new IllegalArgumentException("Only doctors can add records");
        }

We then need to update `AddRecordEntry` to catch the error message:

	public void execute() {
		try {
			recordService.addRecordEntry(user, patient, recordEntry);
		} catch (IllegalArgumentException e) {
			this.errorMessage = e.getMessage();
		}
	}

Now we run the tests and everything works as expected.
