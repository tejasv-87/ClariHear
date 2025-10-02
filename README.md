ClariHear - Android Hearing Assistance App
ClariHear is a prototype Android application designed to provide a complete hearing wellness solution. It allows users to test their hearing across a range of frequencies, view their personalized audiogram results, and use those results to power a real-time hearing assistance feature that amplifies sound based on their unique hearing profile.

Features
Comprehensive Hearing Test: A step-by-step process that guides the user through a hearing screening for both ears across standard audiometric frequencies.

Detailed Results Screen: Displays the hearing thresholds for each ear and provides a clear, plain-language summary of the user's hearing health (e.g., "Normal Hearing," "Mild Hearing Loss").

Real-Time Hearing Assistance: Uses the device's microphone to capture ambient sound, processes it, and plays it back through headphones with amplification tailored to the user's audiogram.

Environment Profiles: Allows the user to select from different modes (Quiet, Noisy, Meeting, Outdoor) to apply a general gain profile suitable for the environment.

Local Data Storage: Securely saves the user's hearing test results on the device for privacy and quick access.

Tech Stack
Platform: Native Android

Language: Java

UI: Android XML Layouts

Data Storage: SharedPreferences with Gson for object serialization

Audio Processing: AudioRecord for microphone input and AudioTrack for low-latency audio playback.

How to Build and Run
To build and run this project, you will need Android Studio.

Clone the repository:

git clone [https://github.com/your-username/ClariHearAssist.git](https://github.com/your-username/ClariHearAssist.git)

Open in Android Studio:

Launch Android Studio.

Select File > Open and navigate to the cloned project directory.

Sync Gradle:

Android Studio will automatically sync the project's dependencies. If not, click the "Sync Project with Gradle Files" button.

Run the App:

Connect an Android device with USB Debugging enabled, or start an Android Emulator.

Select the device from the dropdown menu and click the "Run 'app'" button.

Project Structure
The application is divided into four main activities:

MainActivity.java: The main home screen that provides navigation to the other features.

HearingTestActivity.java: Manages the hearing test flow, guiding the user through each frequency.

ResultsActivity.java: Loads the saved audiogram data from SharedPreferences and displays it.

HearingAssistActivity.java: The core of the real-time assistance feature. It handles audio capture, a simplified processing loop, and playback.

Current Status & Future Work
This project is currently a functional prototype. The core navigation, testing flow, and UI are complete.

The audio processing in HearingAssistActivity.java is currently a simplified gain model. A future version would replace this with a more advanced DSP algorithm (such as an FFT-based approach) to apply precise gain to each frequency band according to the user's audiogram data.
