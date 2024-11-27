// components/PlacementRequestForm/PlacementRequestForm.jsx
import React, { useState, useEffect } from "react";
import {
  getSpecializations,
  getDomains,
  submitPlacementRequest,
} from "./../utils/api";
import Select from "react-select";
import "./PlacementRequestForm.css";

export default function PlacementRequestForm() {
  const [organization, setOrganization] = useState("");
  const [profile, setProfile] = useState("");
  const [description, setDescription] = useState("");
  const [intake, setIntake] = useState("");
  const [minimumGrade, setMinimumGrade] = useState("");
  const [specializationIds, setSpecializationIds] = useState([]);
  const [domainIds, setDomainIds] = useState([]);
  const [specializations, setSpecializations] = useState([]);
  const [domains, setDomains] = useState([]);
  const [responseMessage, setResponseMessage] = useState("");
  const [isResponseVisible, setIsResponseVisible] = useState(false);

  const handleResponseMessage = (message) => {
    setIsResponseVisible(true);
    setResponseMessage(message);

    // Hide the message after 5 seconds
    setTimeout(() => {
      setIsResponseVisible(false);
    }, 5000);
  };

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      setResponseMessage("You are not authenticated.");
      return;
    }

    // Fetch specializations and domains using utility functions
    getSpecializations(token)
      .then((specializations) => setSpecializations(specializations))
      .catch((error) => {
        console.error("Error fetching specializations:", error);
      });

    getDomains(token)
      .then((domains) => setDomains(domains))
      .catch((error) => {
        console.error("Error fetching domains:", error);
      });
  }, []);

  const handleSubmit = (e) => {
    e.preventDefault();

    const token = localStorage.getItem("token");
    if (!token) {
      setResponseMessage("You are not authenticated.");
      setIsResponseVisible(true);
      return;
    }

    if (!organization || !profile || !description) {
      setResponseMessage("Please fill out all required fields.");
      setIsResponseVisible(true);
      return;
    }

    const parsedIntake = parseInt(intake);
    console.log(parsedIntake);
    const parsedMinimumGrade = parseFloat(minimumGrade);

    if (intake.length >= 1 && isNaN(parsedIntake)) {
      setResponseMessage("Please enter a valid number.");
      setIsResponseVisible(true);
      return;
    }

    if (minimumGrade.length >= 1 && isNaN(parsedMinimumGrade)) {
      setResponseMessage("Please enter a valid number.");
      setIsResponseVisible(true);
      return;
    }

    if (parsedIntake < 1) {
      setResponseMessage("Minimum intake must be 1.");
      setIsResponseVisible(true);
      return;
    }

    if (parsedIntake > 99999) {
      console.log("Here inside");
      setResponseMessage(
        "Intake cannot be greater than no of people in college"
      );
      setIsResponseVisible(true);
      return;
    }

    // CGPA validation
    if (parsedMinimumGrade < 0 || parsedMinimumGrade > 4) {
      setResponseMessage("Minimum Grade (CGPA) must be between 0 and 4.");
      setIsResponseVisible(true);
      return;
    }

    const placementRequest = {
      organization,
      profile,
      description,
      intake: parsedIntake,
      minimumGrade: parsedMinimumGrade,
      specialization_id: specializationIds.map((id) => parseInt(id.value)),
      domain_id: domainIds.map((id) => parseInt(id.value)),
    };

    submitPlacementRequest(placementRequest, token)
      .then(() => {
        setResponseMessage("Placement request submitted successfully!");
        setIsResponseVisible(true);

        // Reset form fields
        setOrganization("");
        setProfile("");
        setDescription("");
        setIntake("");
        setMinimumGrade("");
        setSpecializationIds([]);
        setDomainIds([]);
      })
      .catch((error) => {
        if (error.response && error.response.status === 401) {
          handleLogout();
        }
        console.error("Error submitting placement request:", error);
        setResponseMessage("Failed to submit placement request.");
        setIsResponseVisible(true);
      });

    // Hide the response message after 5 seconds
    setTimeout(() => {
      setIsResponseVisible(false);
      setResponseMessage("");
    }, 5000);
  };

  const handleLogout = () => {
    localStorage.clear();
    window.location.href = "/login";
  };

  return (
    <div className="container">
      <button className="logout-button" onClick={handleLogout}>
        Logout
      </button>

      {responseMessage && <p>{responseMessage}</p>}

      <h1 className="formheading">Submit Placement Request</h1>
      <form onSubmit={handleSubmit}>
        <label className="labels" htmlFor="organization">
          Organization:
        </label>
        <input
          type="text"
          id="organization"
          value={organization}
          onChange={(e) => setOrganization(e.target.value)}
        />
        <br />

        <label className="labels" htmlFor="profile">
          Profile:
        </label>
        <input
          type="text"
          id="profile"
          value={profile}
          onChange={(e) => setProfile(e.target.value)}
        />
        <br />

        <label className="labels" htmlFor="description">
          Description:
        </label>
        <textarea
          id="description"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
        />
        <br />

        <label className="labels" htmlFor="intake">
          Intake:
        </label>
        <input
          type="text"
          id="intake"
          value={intake}
          onChange={(e) => setIntake(e.target.value)}
          onBlur={() => {
            if (isNaN(intake)) {
              setIntake("");
            }
          }}
        />
        <br />

        <label className="labels" htmlFor="minimumGrade">
          Minimum Grade:
        </label>
        <input
          type="text"
          id="minimumGrade"
          value={minimumGrade}
          onChange={(e) => setMinimumGrade(e.target.value)}
        />
        <br />

        <label className="labels" htmlFor="specialization_ids">
          Specializations:
        </label>
        <Select
          className="select"
          id="specialization_ids"
          isMulti
          options={specializations}
          value={specializationIds}
          onChange={setSpecializationIds}
          styles={{
            control: (styles) => ({ ...styles, backgroundColor: "black" }),
          }}
        />
        <br />

        <label className="labels" htmlFor="domain_ids">
          Domains:
        </label>
        <Select
          className="select"
          id="domain_ids"
          isMulti
          options={domains}
          value={domainIds}
          onChange={setDomainIds}
          styles={{
            control: (styles) => ({ ...styles, backgroundColor: "black" }),
          }}
        />
        <br />

        <button type="submit">Submit</button>
      </form>
    </div>
  );
}
